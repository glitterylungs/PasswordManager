package com.example.passwordmanager.di

import androidx.room.Room
import com.example.passwordmanager.database.AppDatabase
import com.example.passwordmanager.database.dao.PasswordDao
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

private const val DATABASE_NAME = "app_database"

val databaseModule = module {

    single {
        Room.databaseBuilder(
            context = androidContext(),
            klass = AppDatabase::class.java,
            name = DATABASE_NAME
        ).build()
    }

    single {
        createPasswordDao(
            database = get()
        )
    }
}

private fun createPasswordDao(database: AppDatabase): PasswordDao =
    database.passwordDao()