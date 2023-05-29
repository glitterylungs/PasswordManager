package com.example.passwordmanager.database.dao

import androidx.room.Insert
import androidx.room.Query
import com.example.passwordmanager.database.entity.PasswordDb
import kotlinx.coroutines.flow.Flow

interface PasswordDao {

    @Query("SELECT * FROM password")
    fun getAllPasswords(): Flow<List<PasswordDb>>

    @Insert
    suspend fun insertPassword(password: PasswordDb)
}