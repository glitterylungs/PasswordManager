package com.example.passwordmanager.usecase

import android.util.Base64
import com.example.passwordmanager.repository.PasswordRepository
import com.example.passwordmanager.repository.model.Password
import com.example.passwordmanager.usecase.model.NewPassword

interface UpdatePasswordUseCase {

    suspend fun execute(password: NewPassword)
}

internal class UpdatePasswordUseCaseImpl(
    private val passwordRepository: PasswordRepository,
    private val encryptPasswordUseCase: EncryptPasswordUseCase
) : UpdatePasswordUseCase {

    override suspend fun execute(password: NewPassword) {
        val (iv, encryptedPassword) = encryptPasswordUseCase.execute(password.password)

        val passwordToUpdate = Password(
            id = password.id,
            name = password.name,
            login = password.login,
            password = Base64.encodeToString(encryptedPassword, Base64.DEFAULT),
            iv = Base64.encodeToString(iv, Base64.DEFAULT)
        )

        passwordRepository.updatePassword(passwordToUpdate)
    }
}