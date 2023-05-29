package com.example.passwordmanager.repository.model

data class Password(
    val id: Int,
    val name: String,
    val login: String,
    val password: String,
    val iv: String
)