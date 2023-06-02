package com.example.passwordmanager.usecase

import com.example.passwordmanager.repository.PasswordRepository

interface DeletePasswordUseCase {

    suspend fun execute(id: Int)
}

internal class DeletePasswordUseCaseImpl(
    private val passwordRepository: PasswordRepository
) : DeletePasswordUseCase {

    override suspend fun execute(id: Int) =
        passwordRepository.deletePassword(id)
}