package com.android.multistream.util.pagination

import androidx.lifecycle.MutableLiveData

abstract class Pagination<T>(val listener: PaginationListener<T>) {


    class PagedKeyLoader<T>(val listener: PaginationListener<T>) : PaginationListener<T> by listener {
        val data: MutableList<T> = mutableListOf()
        val dataLiveData = MutableLiveData<List<T>>()

        var nextKey: String? = null
        var initLoad: Boolean = false
        fun loadHandler() {
            if (!initLoad) loadInitial(this) else loadNext(this, nextKey)
        }

        init {
            loadInitial(this)
        }


        fun loadInit(nextKey: String?, data: List<T>?) {
            initLoad = true
            data?.let {
                this.data.addAll(data)
                dataLiveData.postValue(this.data)
            }

            if (nextKey == null) noNextKey() else {
                this.nextKey = nextKey
            }
        }

        fun loadNext(nextKey: String?, data: List<T>?) {
            data?.let {
                this.data.addAll(data)
                dataLiveData.postValue(this.data)
            }
            if (nextKey == null) noNextKey() else this.nextKey = nextKey
        }

        private fun noNextKey() {
            this.nextKey = null
        }
    }
}