package com.cookiedinner.simple_pager

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow

private const val initialized = false

private class PagerSample {
    class SampleViewModel : ViewModel() {
        val pager = Pager(
            config = PagerConfig(
                pageSize = 20,
                prefetchDistance = 20,
                onlyDistinctItems = false
            ),
            coroutineScope = viewModelScope
        ) { nextPage, pageSize ->
            fetchData(nextPage, pageSize)
        }
    }

    @Composable
    fun SampleScreen(viewModel: SampleViewModel) {
        LaunchedEffect(Unit) {
            if (!initialized) {
                viewModel.pager.resetPager()
            }
        }
    }
}

private fun fetchData(nextPage: Int, pageSize: Int): Flow<Result<List<Nothing>>>? = null