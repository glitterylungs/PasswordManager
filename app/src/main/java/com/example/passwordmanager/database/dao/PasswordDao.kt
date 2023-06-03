package com.example.passwordmanager.database.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.passwordmanager.database.entity.PasswordDb
import kotlinx.coroutines.flow.Flow

@Dao
interface PasswordDao {

    @Query("SELECT * FROM password")
    fun getAllPasswords(): Flow<List<PasswordDb?>>

    @Query("SELECT * FROM password WHERE id = :id")
    fun getPassword(id: Int): Flow<PasswordDb>

    @Insert
    suspend fun insertPassword(password: PasswordDb)

    @Query("UPDATE password SET name = :name, login = :login WHERE id = :id")
    suspend fun updatePassword(id: Int, name: String, login: String)

    @Query("DELETE FROM password WHERE id = :id")
    suspend fun deletePassword(id: Int)
}