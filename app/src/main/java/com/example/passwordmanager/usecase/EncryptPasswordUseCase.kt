package com.example.passwordmanager.usecase

import com.example.passwordmanager.manager.CryptographyManager

interface EncryptPasswordUseCase {

    fun execute(password: String): Pair<ByteArray, ByteArray>
}

internal class EncryptPasswordUseCaseImpl(
    private val cryptographyManager: CryptographyManager
) : EncryptPasswordUseCase {

    override fun execute(password: String): Pair<ByteArray, ByteArray> {
        val passwordByteArray = password.toByteArray(Charsets.UTF_8)
        return cryptographyManager.encrypt(passwordByteArray)
    }
}