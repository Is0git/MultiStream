package com.android.multistream.pagination

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.*

class PageLoader<T>(var pagedListener: PagedListener<T>) {

    val data: MutableList<T> = mutableListOf()

    val dataLiveData = MutableLiveData<List<T>>()

    val pageLoadingState = MutableLiveData<PageLoadingStates>()

    var initLoad: Boolean = false

    var loadJob: Job? = null

    init {
       loadJob =  CoroutineScope(Dispatchers.Default).launch { loadHandler() }
    }

    suspend fun loadHandler() {
        pageLoadingState.postValue(PageLoadingStates.LOADING)
        if (!initLoad) loadInitialData() else loadNextData()
    }


    private suspend fun loadInitialData() {
        initLoad = true
        val responseData = when (pagedListener) {

            is PagedKeyListener<T> -> (pagedListener as PagedKeyListener<T>).loadInitial()

            is PagedOffSetListener<T> -> (pagedListener as PagedOffSetListener<T>).run {
                loadInitial(pageOffSet, pageLimit).also { pageOffSet += pageLimit }
            }
            else -> (pagedListener as PagedNumberListener<T>).run {
                loadInitial(page).also { page += 1 }
            }
        }
        responseData?.let { this.data.addAll(it) }
        dataLiveData.postValue(this.data)

        pageLoadingState.postValue(PageLoadingStates.SUCCESS)
    }

    private suspend fun loadNextData() {

        val responseData = when (pagedListener) {

            is PagedKeyListener<T> -> (pagedListener as PagedKeyListener<T>).loadNext(key = null)

            is PagedOffSetListener<T> -> (pagedListener as PagedOffSetListener<T>).run {
                loadNext(pageOffSet, pageLimit).also { pageOffSet += pageLimit }
            }
            else -> (pagedListener as PagedNumberListener<T>).run {
                loadNext(page).also { page += 1 }
            }
        }

        responseData?.let { this.data.addAll(it) }


        dataLiveData.postValue(this.data)
        pageLoadingState.postValue(PageLoadingStates.SUCCESS)
    }

    interface PagedListener<T>

    interface PagedKeyListener<T> : PagedListener<T> {

        var key: String

        suspend fun loadInitial(): List<T>?

        suspend fun loadNext(key: String?): List<T>?

    }

    interface PagedOffSetListener<T> : PagedListener<T> {

        var pageOffSet: Int

        var pageLimit: Int

        suspend fun loadInitial(pageOffSet: Int, pageLimit: Int): List<T>?

        suspend fun loadNext(pageOffSet: Int, pageLimit: Int): List<T>?

    }

    interface PagedNumberListener<T> : PagedListener<T> {

        var page: Int

        suspend fun loadInitial(page: Int): List<T>?

        suspend fun loadNext(page: Int): List<T>?

    }
}

    infix fun RecyclerView.attach(pageLoader: PageLoader<*>) {
        addOnScrollListener(object : RecyclerView.OnScrollListener() {

            var layoutManager = when (this@attach.layoutManager) {
                is GridLayoutManager -> this@attach.layoutManager as GridLayoutManager
                is LinearLayoutManager -> this@attach.layoutManager as LinearLayoutManager
                else -> this@attach.layoutManager as LinearLayoutManager
            }

            var scrolls = 0

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {

                super.onScrolled(recyclerView, dx, dy)
                Log.d("SCROLL", "dx: $dy, scrolls: $scrolls")

                if (dy > 0) {
                    if (pageLoader.pageLoadingState.value != PageLoadingStates.LOADING) {
                        val itemsCount = layoutManager.itemCount
                        val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                        if (itemsCount - lastVisibleItem - 1 < 10 && pageLoader.pageLoadingState.value != PageLoadingStates.LOADING) {
                            pageLoader.pageLoadingState.value = PageLoadingStates.LOADING
                            pageLoader.loadJob =
                                CoroutineScope(Dispatchers.Default).launch { pageLoader.loadHandler() }
                        }
                    }
                }
            }
        })
    }

    infix fun RecyclerView.detach(pageLoader: PageLoader<*>) {
        pageLoader.loadJob?.cancel().also { pageLoader.loadJob = null }
    }

