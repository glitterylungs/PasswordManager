package com.example.passwordmanager.ui.details

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.repository.model.Password
import com.example.passwordmanager.usecase.DeletePasswordUseCase
import com.example.passwordmanager.usecase.GetDecryptedPasswordUseCase
import com.example.passwordmanager.usecase.GetDecryptionCipherUseCase
import com.example.passwordmanager.usecase.GetPasswordUseCase
import com.example.passwordmanager.usecase.UpdatePasswordUseCase
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

    var passwordObject: MutableState<Password?> = mutableStateOf(null)

    var name = mutableStateOf("")
        private set

    var login = mutableStateOf("")
        private set

    var password = mutableStateOf("")
        private set

    var isReadOnly = mutableStateOf(true)
        private set

    var isDialogVisible = mutableStateOf(false)
        private set

    fun getDataById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getPasswordUseCase.execute(id).collect {
                name.value = it.name
                login.value = it.login
                password.value = it.password

                passwordObject.value = it
            }
        }
    }

    fun getDecryptedPassword(password: Password): String =
        getDecryptedPasswordUseCase.execute(password)

    fun getDecryptionCipher(iv: String): Cipher =
        getDecryptionCipherUseCase.execute(iv)

    private fun updatePassword(id: Int, name: String, login: String) =
        viewModelScope.launch(Dispatchers.IO) {
            updatePasswordUseCase.execute(id, name, login)
        }

    fun deletePassword(id: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            deletePasswordUseCase.execute(id)
        }

    fun toggleEditMode(id: Int, name: String, login: String) {
        isReadOnly.value = !isReadOnly.value

        if (isReadOnly.value) {
            updatePassword(id, name, login)
        }
    }

    fun changeIsDialogVisible(isVisible: Boolean) {
        isDialogVisible.value = isVisible
    }
}