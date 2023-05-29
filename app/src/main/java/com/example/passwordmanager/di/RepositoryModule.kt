package com.example.passwordmanager.di

import com.example.passwordmanager.repository.PasswordRepository
import com.example.passwordmanager.repository.PasswordRepositoryImpl
import org.koin.dsl.module

val repositoryModule = module {

    single<PasswordRepository> {
        PasswordRepositoryImpl(
            passwordDao = get(),
            passwordDbToPasswordMapper = get(),
            passwordToPasswordDbMapper = get()
        )
    }
}