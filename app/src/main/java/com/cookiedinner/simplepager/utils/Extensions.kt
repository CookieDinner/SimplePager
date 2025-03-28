package com.cookiedinner.simplepager.utils

fun <T> List<T>.subListSafe(fromIndex: Int, toIndex: Int): List<T> {
    if (this.isEmpty()) return emptyList()
    return if (fromIndex < this.size) {
        this.subList(fromIndex, minOf(toIndex, this.size))
    } else
        emptyList()
}