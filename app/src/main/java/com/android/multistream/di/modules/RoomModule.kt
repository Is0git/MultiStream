package com.android.multistream.di.modules

import android.app.Application
import androidx.room.Room
import com.android.multistream.database.MainDatabase
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object RoomModule {

    @Provides
    @JvmStatic
    @Singleton
    fun getDatabase(application: Application) = synchronized(this) {Room.databaseBuilder(application, MainDatabase::class.java, "main_database").fallbackToDestructiveMigration().build()}

    @Provides
    @JvmStatic
    @Singleton
    fun twitchDao(mainDatabase: MainDatabase ) = mainDatabase.twitchDao()

    @Provides
    @JvmStatic
    @Singleton
    fun searchDao(mainDatabase: MainDatabase ) = mainDatabase.searchDao()
}