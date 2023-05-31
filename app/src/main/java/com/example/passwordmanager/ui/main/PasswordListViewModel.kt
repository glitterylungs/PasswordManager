package com.example.passwordmanager.ui.main

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.passwordmanager.repository.model.Password
import com.example.passwordmanager.usecase.AddPasswordUseCase
import com.example.passwordmanager.usecase.GetPasswordsUseCase
import com.example.passwordmanager.usecase.model.NewPassword
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class PasswordListViewModel(
    private val addPasswordUseCase: AddPasswordUseCase,
    getPasswordsUseCase: GetPasswordsUseCase,
) : ViewModel() {

    var passwords: Flow<List<Password>> = getPasswordsUseCase.execute()

    var name = mutableStateOf("")
    private set

    var login = mutableStateOf("")
    private set

    var password = mutableStateOf("")
    private set

    var isDialogVisible = mutableStateOf(false)
    private set

    private fun clearTextFields() {
        name.value = ""
        login.value = ""
        password.value = ""
    }

    fun addPassword() {
        val newPassword = NewPassword(
            id = DEFAULT_ID,
            name = name.value,
            login = login.value,
            password = password.value
        )

        viewModelScope.launch(Dispatchers.IO) {
            addPasswordUseCase.execute(newPassword)
            clearTextFields()
        }
    }

    fun changeIsDialogVisible(isVisible: Boolean) {
        isDialogVisible.value = isVisible
    }

    companion object {
        private const val DEFAULT_ID = 0
    }
}