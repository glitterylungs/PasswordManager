package com.example.passwordmanager.di

import com.example.passwordmanager.mapper.PasswordDbToPasswordMapper
import com.example.passwordmanager.mapper.PasswordDbToPasswordMapperImpl
import com.example.passwordmanager.mapper.PasswordToPasswordDbMapper
import com.example.passwordmanager.mapper.PasswordToPasswordDbMapperImpl
import org.koin.dsl.module

val mapperModule = module {

    single<PasswordDbToPasswordMapper> {
        PasswordDbToPasswordMapperImpl()
    }

    single<PasswordToPasswordDbMapper> {
        PasswordToPasswordDbMapperImpl()
    }
}