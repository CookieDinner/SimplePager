package com.cookiedinner.simplepager.mocks

import com.cookiedinner.simple_pager.Result
import com.cookiedinner.simple_pager.asResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MockRepository(
    private val mockSource: MockSource
) {
    fun getMocks(
        dirtyList: Boolean,
        page: Int,
        pageSize: Int
    ): Flow<Result<List<Mock>>> {
        return flow {
            val list = mockSource.getMocks(dirtyList, page, pageSize)
            emit(list)
        }.asResult()
    }
}