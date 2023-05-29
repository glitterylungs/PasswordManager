package com.example.passwordmanager.di

import com.example.passwordmanager.manager.KeyManager
import com.example.passwordmanager.manager.KeyManagerImpl
import org.koin.dsl.module

val managerModule = module {

    single<KeyManager> {
        KeyManagerImpl()
    }
}