package com.example.passwordmanager.manager

import android.content.Context
import androidx.biometric.BiometricManager
import androidx.biometric.BiometricPrompt
import androidx.fragment.app.FragmentActivity
import com.example.passwordmanager.provider.BiometricPromptInfoProvider
import javax.crypto.Cipher

interface AuthenticationManager {

    fun canAuthenticate(context: Context): Boolean

    fun authenticate(
        activity: FragmentActivity,
        callback: BiometricPrompt.AuthenticationCallback,
        cipher: Cipher
    )
}

internal class AuthenticationManagerImpl(
    private val biometricPromptInfoProvider: BiometricPromptInfoProvider
) : AuthenticationManager {

    override fun canAuthenticate(context: Context): Boolean =
        BiometricManager.from(context)
            .canAuthenticate(
                BiometricManager.Authenticators.BIOMETRIC_STRONG
                        or BiometricManager.Authenticators.DEVICE_CREDENTIAL
            ) == BiometricManager.BIOMETRIC_SUCCESS

    override fun authenticate(
        activity: FragmentActivity,
        callback: BiometricPrompt.AuthenticationCallback,
        cipher: Cipher
    ) {
        val promptInfo = biometricPromptInfoProvider.createPromptInfo()
        val biometricPrompt = initBiometricPrompt(activity, callback)

        biometricPrompt.authenticate(
            promptInfo,
            BiometricPrompt.CryptoObject(cipher)
        )
    }

    private fun initBiometricPrompt(
        activity: FragmentActivity,
        callback: BiometricPrompt.AuthenticationCallback
    ): BiometricPrompt =
        BiometricPrompt(
            activity,
            activity.mainExecutor,
            callback
        )
}