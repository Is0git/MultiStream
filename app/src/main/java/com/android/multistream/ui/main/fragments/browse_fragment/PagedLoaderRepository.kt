package com.android.multistream.ui.main.fragments.browse_fragment

import android.app.Application
import com.android.multistream.utils.ResponseHandler
import com.example.pagination.PageLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException

abstract class PagedLoaderRepository<T>(
    application: Application,
    startPage: Int,
    pageLimit: Int,
    isAutoLoad: Boolean
) {

    val pageLoader = PageLoader(isAutoLoad, object : PageLoader.PagedNumberListener<T> {

        override suspend fun loadInitial(page: Int): List<T>? {
            try {
                return getInitial(page, pageLimit)
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    ResponseHandler.handleNetworkException(e, application)
                }
            }
            return null
        }

        override suspend fun loadNext(page: Int): List<T>? {
            try {
                return getNext(page, pageLimit)
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    ResponseHandler.handleNetworkException(e, application)
                }
            }
            return null
        }

        override var page: Int = startPage
        override var pageLimit: Int = pageLimit

    })

    abstract suspend fun getInitial(page: Int, pageLimit: Int): List<T>?

    abstract suspend fun getNext(page: Int, pageLimit: Int): List<T>?

}
