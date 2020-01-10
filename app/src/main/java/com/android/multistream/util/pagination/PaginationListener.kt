package com.android.multistream.util.pagination

interface PaginationListener<T> {

    fun loadInitial(pagination: PagedKeyLoader<T>)

    fun loadNext(pagination: PagedKeyLoader<T>, nextKey: String?)


}

interface PagedOffsetListener<T> {

    fun loadInitial(pagination: PagedOffsetLoader<T>)

    fun loadNext(pagination: PagedOffsetLoader<T>)

}