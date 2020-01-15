package com.android.multistream.util.pagination

import androidx.lifecycle.MutableLiveData



    //With reference object
    class PagedKeyLoader<T>(val listener: PaginationListener<T>, val limit: Int) : PaginationListener<T> by listener {
        val data: MutableList<T> = mutableListOf()
        val dataLiveData = MutableLiveData<List<T>>()
        val pageLoadingState = MutableLiveData<PageLoadingStates>()
        var nextKey: String? = null
        var initLoad: Boolean = false

        fun loadHandler() {
            pageLoadingState.postValue(PageLoadingStates.LOADING)
            if (!initLoad) loadInitial(this) else loadNext(this, nextKey)
        }

        init {
            pageLoadingState.postValue(PageLoadingStates.LOADING)
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
            pageLoadingState.postValue(PageLoadingStates.SUCCESS)
        }

        fun loadNext(nextKey: String?, data: List<T>?) {
            data?.let {
                this.data.addAll(data)
                dataLiveData.postValue(this.data)
            }
            if (nextKey == null) noNextKey() else this.nextKey = nextKey
            pageLoadingState.postValue(PageLoadingStates.SUCCESS)
        }

        private fun noNextKey() {
            this.nextKey = null
        }
    }
    //With page id
    class PagedPositionLoader<T>(val listener: PagedPositionListener<T>) : PagedPositionListener<T> by listener {
        val data: MutableList<T> = mutableListOf()
        val dataLiveData = MutableLiveData<List<T>>()
        val pageLoadingState = MutableLiveData<PageLoadingStates>()

        var pageCount: Int = 0
        var initLoad: Boolean = false
        fun loadHandler() {
            pageLoadingState.value = PageLoadingStates.LOADING
            if (!initLoad) loadInitial(this) else loadNext(this)
        }

        init {
            loadInitial(this)
        }


        fun loadInit(data: List<T>?) {
            pageCount++
            initLoad = true
            data?.also {
                this.data.addAll(data)

                dataLiveData.postValue(this.data)

            }
            pageLoadingState.postValue(PageLoadingStates.SUCCESS)
        }

        fun loadNext(data: List<T>?) {
            pageCount++
            data?.also {
                this.data.addAll(data)
                dataLiveData.postValue(this.data)
            }
            pageLoadingState.postValue(PageLoadingStates.SUCCESS)

        }


    }

class PagedOffsetLoader<T>(val listener: PagedOffSetListener<T>, val pageLimit: Int) : PagedOffSetListener<T> by listener {
    val data: MutableList<T> = mutableListOf()
    val dataLiveData = MutableLiveData<List<T>>()
    val pageLoadingState = MutableLiveData<PageLoadingStates>()
    var pageOffset: Int = 0
    var initLoad: Boolean = false
    fun loadHandler() {
        pageLoadingState.value = PageLoadingStates.LOADING
        if (!initLoad) loadInitial(this) else loadNext(this)
    }

    init {
        loadInitial(this)
    }


    fun loadInit(data: List<T>?) {
        pageOffset += 20
        initLoad = true
        data?.also {
            this.data.addAll(data)
            dataLiveData.postValue(this.data)
        }
        pageLoadingState.postValue(PageLoadingStates.SUCCESS)
    }

    fun loadNext(data: List<T>?) {
        pageOffset += 20
        data?.also {
            this.data.addAll(data)
            dataLiveData.postValue(this.data)
        }
        pageLoadingState.postValue(PageLoadingStates.SUCCESS)

    }


}


