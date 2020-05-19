package com.android.multistream.ui.main.fragments.browse_fragment

import android.app.Application
import com.android.multistream.di.main_activity.main_fragments.browse_fragment.BrowseFragmentScope
import com.android.multistream.utils.ResponseHandler
import com.example.pagination.PageLoader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException


@BrowseFragmentScope
abstract class PageOffSetLoaderRepository<T>(
    application: Application,
    pageStartOffSet: Int,
    pageLimit: Int,
    isAutoLoad: Boolean
) {

    val pageLoader = PageLoader(isAutoLoad, object : PageLoader.PagedOffSetListener<T> {
        override var pageOffSet: Int = pageStartOffSet

        override var pageLimit: Int = pageLimit

        override suspend fun loadInitial(pageOffSet: Int, pageLimit: Int): List<T>? {
            try {
                return getInitial(pageOffSet, pageLimit)
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    ResponseHandler.handleNetworkException(e, application)
                }
            }
            return null
        }

        override suspend fun loadNext(pageOffSet: Int, pageLimit: Int): List<T>? {
            try {
                return getNext(pageOffSet, pageLimit)
            } catch (e: IOException) {
                withContext(Dispatchers.Main) {
                    ResponseHandler.handleNetworkException(e, application)
                }
            }
            return null
        }
    })

    abstract suspend fun getInitial(pageOffSet: Int, pageLimit: Int): List<T>?

    abstract suspend fun getNext(pageOffSet: Int, pageLimit: Int): List<T>?

}