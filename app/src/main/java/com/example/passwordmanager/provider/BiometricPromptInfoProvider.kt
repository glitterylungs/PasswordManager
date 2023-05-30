package com.example.passwordmanager.provider

import androidx.biometric.BiometricPrompt.PromptInfo
import com.example.passwordmanager.R

interface BiometricPromptInfoProvider {

    fun createPromptInfo(): PromptInfo
}

internal class BiometricPromptInfoProviderImpl(
    private val resourceProvider: ResourceProvider
) : BiometricPromptInfoProvider {

    override fun createPromptInfo(): PromptInfo =
        PromptInfo.Builder()
            .setTitle(resourceProvider.getString(R.string.biometric_prompt_info_title))
            .setSubtitle(resourceProvider.getString(R.string.biometric_prompt_info_subtitle))
            .setDescription(resourceProvider.getString(R.string.biometric_prompt_info_description))
            .setNegativeButtonText(resourceProvider.getString(R.string.cancel))
            .build()
}