package com.cookiedinner.simple_pager

/**
 * Interface containing all the load states of [Pager]
 * @property Waiting currently waiting with no paging job assigned
 * @property Loading paging job is currently in progress
 * @property Error the latest paging job has threw an exception
 * @property Success the latest paging job has returned a filled/empty list of data
 */
sealed interface LoadState {
    data object Waiting : LoadState
    data object Loading : LoadState
    data class Error(val exception: Throwable) : LoadState
    data object Success : LoadState
}