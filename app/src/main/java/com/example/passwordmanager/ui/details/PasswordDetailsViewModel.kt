package com.example.passwordmanager.ui.details

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.repository.model.Password
import com.example.passwordmanager.usecase.DeletePasswordUseCase
import com.example.passwordmanager.usecase.GetDecryptedPasswordUseCase
import com.example.passwordmanager.usecase.GetDecryptionCipherUseCase
import com.example.passwordmanager.usecase.GetPasswordUseCase
import com.example.passwordmanager.usecase.UpdatePasswordUseCase
import com.example.passwordmanager.usecase.model.NewPassword
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.crypto.Cipher

class PasswordDetailsViewModel(
    private val getDecryptedPasswordUseCase: GetDecryptedPasswordUseCase,
    private val getDecryptionCipherUseCase: GetDecryptionCipherUseCase,
    private val getPasswordUseCase: GetPasswordUseCase,
    private val updatePasswordUseCase: UpdatePasswordUseCase,
    private val deletePasswordUseCase: DeletePasswordUseCase
) : ViewModel() {

    var name = mutableStateOf("")
        private set

    var login = mutableStateOf("")
        private set

    var password = mutableStateOf("")
        private set

    var isReadOnly = mutableStateOf(true)
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

    private fun updatePassword(passwordId: Int) {
        val newPassword = NewPassword(
            id = passwordId,
            name = name.value,
            login = login.value,
            password = password.value
        )

        viewModelScope.launch(Dispatchers.IO) {
            updatePasswordUseCase.execute(newPassword)
        }
    }

    fun deletePassword(id: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            deletePasswordUseCase.execute(id)
        }

    fun toggleEditMode(id: Int) {
        isReadOnly.value = !isReadOnly.value

        if (isReadOnly.value) {
            updatePassword(id)
        }
    }
}