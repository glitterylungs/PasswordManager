package com.example.passwordmanager.usecase

import com.example.passwordmanager.repository.PasswordRepository
import com.example.passwordmanager.repository.model.Password
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface GetPasswordsUseCase {

    fun execute(): Flow<List<Password?>>
}

internal class GetPasswordsUseCaseImpl(
    private val passwordRepository: PasswordRepository
) : GetPasswordsUseCase {
    override fun execute(): Flow<List<Password?>> =
        passwordRepository.getAllPasswords().map {
            it.sortedBy { password ->
                password?.name
            }
        }
}