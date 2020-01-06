package com.android.multistream.util.pagination

interface PaginationListener<T> {

    fun loadInitial(pagination: Pagination.PagedKeyLoader<T>)

    fun loadNext(pagination: Pagination.PagedKeyLoader<T>, nextKey: String?)
}