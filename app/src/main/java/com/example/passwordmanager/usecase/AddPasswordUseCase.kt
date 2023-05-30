package com.example.passwordmanager.usecase

import android.util.Base64.DEFAULT
import android.util.Base64.encodeToString
import com.example.passwordmanager.repository.PasswordRepository
import com.example.passwordmanager.repository.model.Password
import com.example.passwordmanager.usecase.model.NewPassword

interface AddPasswordUseCase {

    suspend fun execute(password: NewPassword)
}

internal class AddPasswordUseCaseImpl(
    private val passwordRepository: PasswordRepository,
    private val encryptPasswordUseCase: EncryptPasswordUseCase
) : AddPasswordUseCase {
    override suspend fun execute(password: NewPassword) {
        val (iv, encryptedPassword) = encryptPasswordUseCase.execute(password.password)

        val passwordToAdd = Password(
            id = password.id,
            name = password.name,
            login = password.login,
            password = encodeToString(encryptedPassword, DEFAULT),
            iv = encodeToString(iv, DEFAULT)
        )

        passwordRepository.insertPassword(passwordToAdd)
    }
}