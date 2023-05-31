package com.example.passwordmanager.provider

import android.content.Context
import android.widget.Toast

interface ToastProvider {

    fun show(message: CharSequence)
}

internal class ToastProviderImpl(
    private val context: Context
) : ToastProvider {

    override fun show(message: CharSequence) =
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}