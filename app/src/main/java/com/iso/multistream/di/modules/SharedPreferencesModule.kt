package com.iso.multistream.di.modules

import android.app.Application
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.iso.multistream.di.qualifiers.AuthPreferencesQualifier
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

const val AUTH_KEYS = "AUTH_TOKENS"

@Module
object SharedPreferencesModule {
    @JvmStatic
    @Provides
    @Singleton
    fun masterKeyAlias() = kotlin.run {
        val keyGenerator = MasterKeys.AES256_GCM_SPEC
        MasterKeys.getOrCreate(keyGenerator)

    }


    @Provides
    @Singleton
    @JvmStatic
    @AuthPreferencesQualifier
    fun authEncryptedSharedPreferences(
        masterKey: String,
        application: Application
    ): SharedPreferences {
        return EncryptedSharedPreferences.create(
            AUTH_KEYS,
            masterKey,
            application,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    @Provides
    @Singleton
    @JvmStatic
    @AuthPreferencesQualifier
    fun authEncryptedSharedPreferencesEditor(@AuthPreferencesQualifier sharedPreferences: SharedPreferences): SharedPreferences.Editor =
        sharedPreferences.edit()
}