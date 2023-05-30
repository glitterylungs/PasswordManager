package com.example.passwordmanager.usecase

import android.util.Base64.DEFAULT
import android.util.Base64.decode
import com.example.passwordmanager.manager.CryptographyManager

import javax.crypto.Cipher

interface GetDecryptionCipherUseCase {

    fun execute(iv: String): Cipher
}

internal class GetDecryptionCipherUseCaseImpl(
    private val cryptographyManager: CryptographyManager
) : GetDecryptionCipherUseCase {

    override fun execute(iv: String): Cipher =
        cryptographyManager.getDecryptionCipherForIv(decode(iv, DEFAULT))
}