package com.cookiedinner.simple_pager

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

/**
 * Pager class responsible for managing the paged list of items. WARNING: Requires a call to [resetPager] to begin paging. Paging begins from page 1.
 * @param config configuration class
 * @param coroutineScope viewModel's coroutine scope for handling the paging requests from [pagingSource]
 * @param pagingSource lambda that returns the flow containing a newly downloaded page based on the 'nextPage' and 'pageSize' parameters
 * @sample PagerSample
 * @property lazyPagingItemsFlow [StateFlow] containing the [LazyPagingItems] wrapper class for the currently paged items
 * @property resetPager function that should be called to reset the pager state after eg. changes to filters, search query
 */
class Pager<T>(
    private var config: PagerConfig = PagerConfig(),
    private val coroutineScope: CoroutineScope,
    private val pagingSource: (nextPage: Int, pageSize: Int) -> Flow<Result<List<T>>>?
) {
    private var pagerActive = true
    private var pagedItemsSize = 0
    private var nextPage = MutableStateFlow(config.firstPageIndex)
    private val _lazyPagingItems = MutableStateFlow<LazyPagingItems<T>>(
        LazyPagingItems { reachedIndex ->
            if (pagerActive && reachedIndex >= pagedItemsSize - config.prefetchDistance) {
                Log.d("Tests", "Trying to fetch next page with config: $config")
                pageMore()
            }
        }
    )
    val lazyPagingItemsFlow = _lazyPagingItems.asStateFlow()

    fun editConfig(config: PagerConfig) {
        this.config = config
        resetPager()
    }

    private suspend fun getMoreItems(previousItems: List<T> = emptyList()): List<T> {
        var list = emptyList<T>()
        pagingSource(nextPage.value, config.pageSize)
            ?.collect { result ->
                when (result) {
                    is Result.Loading -> {
                        _lazyPagingItems.update {
                            if (it.loadState != LoadState.Waiting) {
                                it.copy(loadState = LoadState.Loading)
                            } else
                                it
                        }
                    }

                    is Result.Error -> {
                        _lazyPagingItems.update {
                            it.copy(loadState = LoadState.Error(result.exception))
                        }
                    }

                    is Result.Success -> {
                        nextPage.update { it + 1 }
                        val currentlyPagedItems = _lazyPagingItems.value.items ?: emptyList()
                        list = if (config.onlyDistinctItems && result.data.any { currentlyPagedItems.contains(it) || previousItems.contains(it)}) {
                            result.data + getMoreItems(previousItems + result.data)
                        } else {
                            result.data
                        }
                    }
                }
            }
        return list
    }

    private var pagingJob: Job? = null
    private fun pageMore() {
        if (pagingJob?.isActive != true) {
            pagingJob = coroutineScope.launch(Dispatchers.IO) {
                val oldItems = _lazyPagingItems.value.items ?: emptyList()
                val newItems = if (config.onlyDistinctItems)
                    getMoreItems().distinct().filter { !oldItems.contains(it) }
                else
                    getMoreItems()
                if (newItems.isEmpty()) {
                    if (config.deactivatePagerOnEndReached) {
                        deactivatePager()
                    }
                    _lazyPagingItems.update {
                       it.copy(
                            items = oldItems,
                            loadState = LoadState.Success
                        )
                    }
                    return@launch
                }
                val newList = oldItems + newItems
                pagedItemsSize = newList.size
                _lazyPagingItems.update {
                    it.copy(
                        items = newList,
                        loadState = LoadState.Success
                    )
                }
            }
        }
    }

    /**
     * Function responsible for resetting the pager to the initial state after any kinds of filter changes etc.
     */
    fun resetPager() {
        deactivatePager()
        pagingJob?.cancel()
        pagedItemsSize = 0
        nextPage.update { config.firstPageIndex }
        _lazyPagingItems.update {
            it.copy(
                items = null,
                loadState = LoadState.Waiting
            )
        }
        activatePager()
        _lazyPagingItems.value.onIndexReached(-1)
    }

    fun activatePager() {
        pagerActive = true
    }

    fun deactivatePager() {
        pagerActive = false
    }
}