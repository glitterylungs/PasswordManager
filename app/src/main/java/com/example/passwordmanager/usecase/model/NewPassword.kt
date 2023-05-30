package com.example.passwordmanager.usecase.model

data class NewPassword(
    val id: Int,
    val name: String,
    val login: String,
    val password: String
)