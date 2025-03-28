package com.cookiedinner.simple_pager

import androidx.annotation.IntRange

/**
 * Config class responsible for setting the [Pager] parameters.
 * @param pageSize number of items in a single page
 * @param prefetchDistance how many visible items before the end of current list should the [Pager] fetch the next page
 * @param firstPageIndex index of the first page of the source list
 * @param onlyDistinctItems whether or not list should contain only distinct items
 * @param deactivatePagerOnEndReached whether or not the [Pager] should deactivate upon reaching the end of the source list
 */
data class PagerConfig(
    @IntRange(from = 1) val pageSize: Int = 20,
    @IntRange(from = 1) val prefetchDistance: Int = pageSize,
    @IntRange(from = 0) val firstPageIndex: Int = 0,
    val onlyDistinctItems: Boolean = true,
    val deactivatePagerOnEndReached: Boolean = true,
)