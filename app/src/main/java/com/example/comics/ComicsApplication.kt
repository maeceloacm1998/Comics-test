package com.example.comics

import android.app.Application
import com.example.comics.data.di.MainModule
import com.example.comics.network.NetworkModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.GlobalContext.startKoin

class ComicsApplication: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ComicsApplication)
            modules(NetworkModule.modules)
            modules(MainModule.modules)
        }
    }
}