package com.example.passwordmanager.usecase

import android.util.Base64.DEFAULT
import android.util.Base64.decode
import com.example.passwordmanager.manager.CryptographyManager
import com.example.passwordmanager.repository.model.Password

interface GetDecryptedPasswordUseCase {

    fun execute(password: Password): String
}

internal class GetDecryptedPasswordUseCaseImpl(
    private val cryptographyManager: CryptographyManager
) : GetDecryptedPasswordUseCase {
    override fun execute(password: Password): String =
        cryptographyManager.decrypt(
            decode(password.password, DEFAULT),
            decode(password.iv, DEFAULT)
        ).toString(Charsets.UTF_8)
}