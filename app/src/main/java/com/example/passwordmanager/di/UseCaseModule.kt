package com.example.passwordmanager.di

import com.example.passwordmanager.usecase.AddPasswordUseCase
import com.example.passwordmanager.usecase.AddPasswordUseCaseImpl
import com.example.passwordmanager.usecase.DeletePasswordUseCase
import com.example.passwordmanager.usecase.DeletePasswordUseCaseImpl
import com.example.passwordmanager.usecase.EncryptPasswordUseCase
import com.example.passwordmanager.usecase.EncryptPasswordUseCaseImpl
import com.example.passwordmanager.usecase.GetDecryptedPasswordUseCase
import com.example.passwordmanager.usecase.GetDecryptedPasswordUseCaseImpl
import com.example.passwordmanager.usecase.GetDecryptionCipherUseCase
import com.example.passwordmanager.usecase.GetDecryptionCipherUseCaseImpl
import com.example.passwordmanager.usecase.GetPasswordUseCase
import com.example.passwordmanager.usecase.GetPasswordUseCaseImpl
import com.example.passwordmanager.usecase.GetPasswordsUseCase
import com.example.passwordmanager.usecase.GetPasswordsUseCaseImpl
import com.example.passwordmanager.usecase.UpdatePasswordUseCase
import com.example.passwordmanager.usecase.UpdatePasswordUseCaseImpl
import org.koin.dsl.module

val useCaseModule = module {

    single<AddPasswordUseCase> {
        AddPasswordUseCaseImpl(
            passwordRepository = get(),
            encryptPasswordUseCase = get()
        )
    }

    single<EncryptPasswordUseCase> {
        EncryptPasswordUseCaseImpl(
            cryptographyManager = get()
        )
    }

    single<GetDecryptionCipherUseCase> {
        GetDecryptionCipherUseCaseImpl(
            cryptographyManager = get()
        )
    }

    single<GetDecryptedPasswordUseCase> {
        GetDecryptedPasswordUseCaseImpl(
            cryptographyManager = get()
        )
    }

    single<GetPasswordsUseCase> {
        GetPasswordsUseCaseImpl(
            passwordRepository = get()
        )
    }

    single<GetPasswordUseCase> {
        GetPasswordUseCaseImpl(
            passwordRepository = get()
        )
    }

    single<DeletePasswordUseCase> {
        DeletePasswordUseCaseImpl(
            passwordRepository = get()
        )
    }

    single<UpdatePasswordUseCase> {
        UpdatePasswordUseCaseImpl(
            passwordRepository = get(),
            encryptPasswordUseCase = get()
        )
    }
}