package com.example.passwordmanager.manager

import android.security.keystore.KeyGenParameterSpec
import android.security.keystore.KeyProperties
import com.example.passwordmanager.constants.CryptographyConstants.BLOCK_MODE
import com.example.passwordmanager.constants.CryptographyConstants.ENCRYPTION_PADDING
import com.example.passwordmanager.constants.CryptographyConstants.KEY_ALGORITHM
import java.security.KeyStore
import javax.crypto.KeyGenerator
import javax.crypto.SecretKey

interface KeyManager {

    fun getKey(): SecretKey
}

internal class KeyManagerImpl : KeyManager {

    private val keyStore = KeyStore.getInstance("AndroidKeyStore").apply {
        load(null)
    }

    override fun getKey(): SecretKey {
        val existingKey = keyStore.getEntry(KEY_ALIAS, null) as? KeyStore.SecretKeyEntry
        return existingKey?.secretKey ?: createKey()
    }

    private fun createKey(): SecretKey =
        KeyGenerator.getInstance(KEY_ALGORITHM).apply {
            init(
                KeyGenParameterSpec.Builder(
                    KEY_ALIAS,
                    KeyProperties.PURPOSE_ENCRYPT or KeyProperties.PURPOSE_DECRYPT
                )
                    .setBlockModes(BLOCK_MODE)
                    .setEncryptionPaddings(ENCRYPTION_PADDING)
                    .setUserAuthenticationRequired(false)
                    .setRandomizedEncryptionRequired(true)
                    .build()
            )
        }.generateKey()

    companion object {
        private const val KEY_ALIAS = "secret_key"
    }
}