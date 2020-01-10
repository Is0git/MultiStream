package com.android.multistream.util.pagination

import androidx.lifecycle.MutableLiveData



    //With reference object
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
    //With page id
    class PagedOffsetLoader<T>(val listener: PagedOffsetListener<T>) : PagedOffsetListener<T> by listener {
        val data: MutableList<T> = mutableListOf()
        val dataLiveData = MutableLiveData<List<T>>()

        var pageCount: Int = 0
        var initLoad: Boolean = false
        fun loadHandler() {
            if (!initLoad) loadInitial(this) else loadNext(this)
        }

        init {
            loadInitial(this)
        }


        fun loadInit(data: List<T>?) {
            initLoad = true
            data?.also {
                this.data.addAll(data)
                dataLiveData.postValue(this.data)
                pageCount++
            }
        }

        fun loadNext(data: List<T>?) {
            data?.also {
                this.data.addAll(data)
                dataLiveData.postValue(this.data)
                pageCount++
            }

        }


    }
