package com.example.passwordmanager.di

import com.example.passwordmanager.provider.BiometricPromptInfoProvider
import com.example.passwordmanager.provider.BiometricPromptInfoProviderImpl
import com.example.passwordmanager.provider.ResourceProvider
import com.example.passwordmanager.provider.ResourceProviderImpl
import com.example.passwordmanager.provider.ToastProvider
import com.example.passwordmanager.provider.ToastProviderImpl
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val providerModule = module {

    single<ResourceProvider> {
        ResourceProviderImpl(
            context = androidContext()
        )
    }

    single<BiometricPromptInfoProvider> {
        BiometricPromptInfoProviderImpl(
            resourceProvider = get()
        )
    }

    single<ToastProvider> {
        ToastProviderImpl(
            context = androidContext()
        )
    }
}