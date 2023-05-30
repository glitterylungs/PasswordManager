package com.example.passwordmanager

import android.app.Application
import com.example.passwordmanager.di.databaseModule
import com.example.passwordmanager.di.managerModule
import com.example.passwordmanager.di.mapperModule
import com.example.passwordmanager.di.providerModule
import com.example.passwordmanager.di.repositoryModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@MainApplication)
            modules(
                databaseModule,
                repositoryModule,
                mapperModule,
                managerModule,
                providerModule,
            )
        }
    }
}