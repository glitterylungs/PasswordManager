package com.example.passwordmanager.ui.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.repository.model.Password
import com.example.passwordmanager.usecase.GetDecryptedPasswordUseCase
import com.example.passwordmanager.usecase.GetDecryptionCipherUseCase
import com.example.passwordmanager.usecase.GetPasswordUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.crypto.Cipher

class PasswordDetailsViewModel(
    private val getDecryptedPasswordUseCase: GetDecryptedPasswordUseCase,
    private val getDecryptionCipherUseCase: GetDecryptionCipherUseCase,
    private val getPasswordUseCase: GetPasswordUseCase
) : ViewModel() {

    var name = mutableStateOf("")
        private set

    var login = mutableStateOf("")
        private set

    var password = mutableStateOf("")
        private set

    fun getDataById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getPasswordUseCase.execute(id).collect {
                name.value = it.name
                login.value = it.login
                password.value = it.password
            }
        }
    }

    fun getDecryptedPassword(password: Password): String =
        getDecryptedPasswordUseCase.execute(password)

    fun getDecryptionCipher(iv: String): Cipher =
        getDecryptionCipherUseCase.execute(iv)
}