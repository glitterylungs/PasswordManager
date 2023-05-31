package com.example.passwordmanager.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.passwordmanager.database.entity.PasswordDb
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {

    @Query("SELECT * FROM password")
    fun getAllPasswords(): Flow<List<PasswordDb>>

    @Query("SELECT * FROM password WHERE id = :id")
    fun getPassword(id: Int): Flow<PasswordDb>

    @Insert
    suspend fun insertPassword(password: PasswordDb)
}