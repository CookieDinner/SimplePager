package com.cookiedinner.simple_pager

import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect

/**
 * Wrapper class for the paged items list
 * @param items list of all currently paged items
 * @param loadState state corresponding to the currently running [Pager] operations
 * @param onIndexReached a callback that gets called with the index of the latest rendered items on the screen
 */
data class LazyPagingItems<T>(
    val items: List<T?>? = null,
    val loadState: LoadState = LoadState.Waiting,
    val onIndexReached: (index: Int) -> Unit = {}
)

inline fun <T> LazyListScope.items(
    pagingItems: LazyPagingItems<T>,
    crossinline itemContent: @Composable (LazyItemScope.(item: T) -> Unit)
) {
    if (pagingItems.items != null) {
        itemsIndexed(pagingItems.items) { index, item ->
            LaunchedEffect(Unit) {
                pagingItems.onIndexReached(index)
            }
            if (item != null)
                itemContent(item)
        }
    }
}

inline fun <T> LazyListScope.itemsIndexed(
    pagingItems: LazyPagingItems<T>,
    crossinline itemContent: @Composable (LazyItemScope.(index: Int, item: T) -> Unit)
) {
    if (pagingItems.items != null) {
        itemsIndexed(pagingItems.items) { index, item ->
            LaunchedEffect(Unit) {
                pagingItems.onIndexReached(index)
            }
            if (item != null)
                itemContent(index, item)
        }
    }
}