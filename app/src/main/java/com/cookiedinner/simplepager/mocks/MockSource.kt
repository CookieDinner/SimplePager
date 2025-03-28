package com.cookiedinner.simplepager.mocks

interface MockSource {
    suspend fun getMocks(dirtyList: Boolean, page: Int, pageSize: Int): List<Mock>
}