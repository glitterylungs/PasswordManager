package com.example.passwordmanager.usecase

import com.example.passwordmanager.repository.PasswordRepository
import com.example.passwordmanager.repository.model.Password
import kotlinx.coroutines.flow.Flow

interface GetPasswordUseCase {

    fun execute(id: Int): Flow<Password>
}

internal class GetPasswordUseCaseImpl(
    private val passwordRepository: PasswordRepository
) : GetPasswordUseCase {

    override fun execute(id: Int): Flow<Password> =
        passwordRepository.getPassword(id)
}