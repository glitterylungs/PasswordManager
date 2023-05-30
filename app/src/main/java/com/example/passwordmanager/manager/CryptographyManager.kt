package com.example.passwordmanager.manager

import com.example.passwordmanager.constants.CryptographyConstants.TRANSFORMATION
import javax.crypto.Cipher
import javax.crypto.spec.IvParameterSpec

interface CryptographyManager {

    fun getDecryptionCipherForIv(iv: ByteArray): Cipher

    fun encrypt(data: ByteArray): Pair<ByteArray, ByteArray>

    fun decrypt(data: ByteArray, iv: ByteArray): ByteArray
}

internal class CryptographyManagerImpl(
    private val keyManager: KeyManager
) : CryptographyManager {

    override fun getDecryptionCipherForIv(iv: ByteArray): Cipher =
        Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.DECRYPT_MODE, keyManager.getKey(), IvParameterSpec(iv))
        }

    override fun encrypt(data: ByteArray): Pair<ByteArray, ByteArray> {
        val encryptionCipher = getEncryptionCipher()
        return Pair(encryptionCipher.iv, encryptionCipher.doFinal(data))
    }

    override fun decrypt(data: ByteArray, iv: ByteArray): ByteArray =
        getDecryptionCipherForIv(iv).doFinal(data)

    private fun getEncryptionCipher(): Cipher =
        Cipher.getInstance(TRANSFORMATION).apply {
            init(Cipher.ENCRYPT_MODE, keyManager.getKey())
        }
}