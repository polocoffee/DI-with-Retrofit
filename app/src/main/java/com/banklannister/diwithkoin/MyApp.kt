package com.banklannister.diwithkoin

import android.app.Application
import com.banklannister.diwithkoin.di.apiModule
import com.banklannister.diwithkoin.di.photoModule
import org.koin.core.context.startKoin

class MyApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            modules(apiModule, photoModule)
        }
    }
}