package com.example.passwordmanager.di

import com.example.passwordmanager.manager.AuthenticationManager
import com.example.passwordmanager.manager.AuthenticationManagerImpl
import com.example.passwordmanager.manager.CryptographyManager
import com.example.passwordmanager.manager.CryptographyManagerImpl
import com.example.passwordmanager.manager.KeyManager
import com.example.passwordmanager.manager.KeyManagerImpl
import org.koin.dsl.module

val managerModule = module {

    single<KeyManager> {
        KeyManagerImpl()
    }

    single<CryptographyManager> {
        CryptographyManagerImpl(
            keyManager = get()
        )
    }

    single<AuthenticationManager> {
        AuthenticationManagerImpl(
            biometricPromptInfoProvider = get()
        )
    }
}