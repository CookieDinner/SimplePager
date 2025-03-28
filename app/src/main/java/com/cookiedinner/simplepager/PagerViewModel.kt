package com.cookiedinner.simplepager

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cookiedinner.simple_pager.Pager
import com.cookiedinner.simple_pager.PagerConfig
import com.cookiedinner.simplepager.mocks.MockRepository
import com.cookiedinner.simplepager.mocks.MockSourceImpl

class PagerViewModel: ViewModel() {
    private val repository = MockRepository(MockSourceImpl())
    private var dirtyList = false
    private val pager = Pager(
        config = PagerConfig(
            pageSize = 2,
            prefetchDistance = 1,
            firstPageIndex = 0,
            onlyDistinctItems = true,
            deactivatePagerOnEndReached = true
        ),
        coroutineScope = viewModelScope
    ) { nextPage, pageSize ->
        repository.getMocks(dirtyList, nextPage, pageSize)
    }
    val mocks = pager.lazyPagingItemsFlow

    fun beginPaging() = pager.resetPager()

    fun changePagerConfig(config: PagerConfig) = pager.editConfig(config)

    fun changeMockListType(dirtyList: Boolean) {
        this.dirtyList = dirtyList
        pager.resetPager()
    }
}