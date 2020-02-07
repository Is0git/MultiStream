package com.android.multistream.utils.pagination

interface PaginationListener<T> {

    fun loadInitial(pagination: PagedKeyLoader<T>)

    fun loadNext(pagination: PagedKeyLoader<T>, nextKey: String?)


}

interface PagedPositionListener<T> {

    fun loadInitial(pagination: PagedPositionLoader<T>)

    fun loadNext(pagination: PagedPositionLoader<T>)



}


interface PagedOffSetListener<T> {

    fun loadInitial(pagination: PagedOffsetLoader<T>)

    fun loadNext(pagination: PagedOffsetLoader<T>)
}