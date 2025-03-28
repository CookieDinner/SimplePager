package com.cookiedinner.simple_pager

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.LazyListScope
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

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
        item {
            AnimatedVisibility(
                visible = pagingItems.loadState is LoadState.Loading,
                enter = fadeIn(tween(500)) + expandVertically(tween(300)),
                exit = fadeOut(tween(300)) + shrinkVertically(tween(500))
            ) {
                CircularProgressIndicator(modifier = Modifier.padding(vertical = 12.dp))
            }
        }
    }
}