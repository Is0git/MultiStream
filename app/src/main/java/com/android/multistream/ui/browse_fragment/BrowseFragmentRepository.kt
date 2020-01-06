package com.android.multistream.ui.browse_fragment

import android.app.Application
import android.widget.Toast
import androidx.paging.toLiveData
import com.android.multistream.database.dao.TwitchDao
import com.android.multistream.di.MainActivity.browse_fragment.BrowseFragmentScope
import com.android.multistream.di.TwitchRetrofitQualifier
import com.android.multistream.network.twitch.TwitchService
import com.android.multistream.network.twitch.models.Data
import com.android.multistream.util.pagination.Pagination
import com.android.multistream.util.pagination.PaginationListener
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import java.io.IOException
import javax.inject.Inject


@BrowseFragmentScope
class BrowseFragmentRepository @Inject constructor(
    val twitchDao: TwitchDao, @TwitchRetrofitQualifier val retrofit: Retrofit,
    val application: Application
) {
    val service = retrofit.create(TwitchService::class.java)
    val topGames = twitchDao.getTopGames().toLiveData(5)
    var job: Job? = null


   val listener = object : PaginationListener<Data> {
       override fun loadInitial(pagination: Pagination.PagedKeyLoader<Data>) {
           job = CoroutineScope(Dispatchers.IO).launch {
               try {
                   val result = service.getTopGames(null)
                   when {
                       result.isSuccessful -> pagination.loadInit(
                           result.body()?.pagination?.cursor,
                           result.body()?.data
                       )
                   }
               } catch (e: IOException) {
                   Toast.makeText(application, "Connect to internet", Toast.LENGTH_LONG).show()
               }
           }
       }

       override fun loadNext(pagination: Pagination.PagedKeyLoader<Data>, nextKey: String?) {
           job = CoroutineScope(Dispatchers.IO).launch {
               try {
                   val result = service.getTopGames(nextKey)
                   when {
                       result.isSuccessful -> pagination.loadNext(
                           result.body()?.pagination?.cursor,
                           result.body()?.data
                       )
                   }
               } catch (e: IOException) {
                   Toast.makeText(application, "Connect to internet", Toast.LENGTH_LONG).show()
               }
           }
       }
   }


//    val paginationListener = object : PaginationListener {
//        override fun loadInitial() {

//        }
//
//        override fun loadNext() {
//            CoroutineScope(Dispatchers.IO).launch {
//                try {
//                    val result = service.getTopGames()
//                    when {
//                        result.isSuccessful -> result.body()
//                    }
//                } catch (e: IOException) {
//                    Toast.makeText(application, "Connect to internet", Toast.LENGTH_LONG).show()
//                }
//            }
//        }
//
//    }

//    suspend fun updateTopGames() {
//        try {
//            val result = service.getTopGames()
//            when {
//                result.isSuccessful -> result.body()?.apply {
//                    val res = withContext(Dispatchers.IO) {
//                        data.map {
//                            TopGames(
//                                it.boxArtUrl,
//                                it.id!!,
//                                it.name
//                            )
//                        }
//                    }
//                    twitchDao.deleteAllFromTopGames()
//                    twitchDao.insertTopGames(res)
//                }
//            }
//        } catch (e: IOException) {
//            Toast.makeText(application, "Connect to internet", Toast.LENGTH_LONG).show()
//        }
//
//
//    }
}