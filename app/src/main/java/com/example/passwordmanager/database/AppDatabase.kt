package com.example.passwordmanager.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.passwordmanager.database.dao.PasswordDao
import com.example.passwordmanager.database.entity.PasswordDb

@Database(entities = [PasswordDb::class], version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun passwordDao(): PasswordDao
}