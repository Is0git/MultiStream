package com.android.multistream.pagination.listeners

import com.android.multistream.pagination.PagedKeyLoader
import com.android.multistream.pagination.PagedOffsetLoader
import com.android.multistream.pagination.PagedPositionLoader

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