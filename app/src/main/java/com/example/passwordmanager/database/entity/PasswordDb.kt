package com.example.passwordmanager.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "password")
data class PasswordDb(
    @ColumnInfo(name = "name")
    val name: String,
    @ColumnInfo(name = "login")
    val login: String,
    @ColumnInfo(name = "password")
    val password: String,
    @ColumnInfo(name = "initialization_vector")
    val iv: String
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}