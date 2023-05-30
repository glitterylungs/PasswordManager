package com.example.passwordmanager.provider

import android.content.Context
import androidx.annotation.StringRes

interface ResourceProvider {

    fun getString(@StringRes string: Int): String
}

internal class ResourceProviderImpl(
    private val context: Context
) : ResourceProvider {

    override fun getString(string: Int): String = context.getString(string)

}