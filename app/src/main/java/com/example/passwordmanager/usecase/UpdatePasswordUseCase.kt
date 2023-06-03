package com.example.passwordmanager.usecase

import com.example.passwordmanager.repository.PasswordRepository

interface UpdatePasswordUseCase {

    suspend fun execute(id: Int, name: String, login: String)
}

internal class UpdatePasswordUseCaseImpl(
    private val passwordRepository: PasswordRepository
) : UpdatePasswordUseCase {

    override suspend fun execute(id: Int, name: String, login: String) =
        passwordRepository.updatePassword(id, name, login)
}