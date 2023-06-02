package com.example.passwordmanager.di

import com.example.passwordmanager.ui.details.PasswordDetailsViewModel
import com.example.passwordmanager.ui.main.PasswordListViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {

    viewModel {
        PasswordListViewModel(
            addPasswordUseCase = get(),
            getPasswordsUseCase = get()
        )
    }

    viewModel {
        PasswordDetailsViewModel(
            getDecryptedPasswordUseCase = get(),
            getDecryptionCipherUseCase = get(),
            getPasswordUseCase = get(),
            updatePasswordUseCase = get(),
            deletePasswordUseCase = get()
        )
    }
}