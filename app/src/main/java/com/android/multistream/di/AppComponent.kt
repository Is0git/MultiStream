package com.android.multistream.di

import android.app.Application
import com.android.multistream.App
import com.android.multistream.di.modules.AuthModule
import com.android.multistream.di.modules.RetrofitModule
import com.android.multistream.di.modules.RoomModule
import com.android.multistream.di.modules.SharedPreferencesModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Component(modules = [AndroidInjectionModule::class, ActivityBuilder::class, RetrofitModule::class, SharedPreferencesModule::class, RoomModule::class, AuthModule::class])
@Singleton
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    interface Builder {
        fun build(): AppComponent

        @BindsInstance
        fun application(application: Application): Builder
    }
}