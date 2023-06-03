package com.example.passwordmanager.ui.details

import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.fragment.app.FragmentActivity
import com.example.passwordmanager.R
import com.example.passwordmanager.manager.AuthenticationManager
import com.example.passwordmanager.provider.ToastProvider
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordDetails(
    viewModel: PasswordDetailsViewModel = koinViewModel(),
    id: Int,
    navigateToPreviousScreen: () -> Unit
) {

    val context = LocalContext.current
    val authenticationManager = koinInject<AuthenticationManager>()
    val toastProvider = koinInject<ToastProvider>()

    var isPasswordVisible by remember { mutableStateOf(false) }
    var decryptedPassword by remember { mutableStateOf("") }

    var name by remember { viewModel.name }
    var login by remember { viewModel.login }
    val passwordObject by remember { viewModel.passwordObject }

    val isReadOnly by remember { viewModel.isReadOnly }
    val isDialogVisible by remember { viewModel.isDialogVisible }


    LaunchedEffect(true) {
        viewModel.getDataById(id)
    }

    if (isDialogVisible)
        DeletePasswordDialog(
            onDismiss = {
                viewModel.changeIsDialogVisible(false)
            },
            onSave = {
                viewModel.changeIsDialogVisible(false)
                navigateToPreviousScreen()
                viewModel.deletePassword(id)
            }
        )

    passwordObject?.let { passwordObj ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        Text(
                            text = "Details",
                            style = MaterialTheme.typography.headlineSmall
                        )
                    },
                    navigationIcon = {
                        IconButton(onClick = navigateToPreviousScreen) {
                            Icon(
                                imageVector = Icons.Default.ArrowBack,
                                contentDescription = "Return"
                            )
                        }
                    },
                    actions = {
                        IconButton(onClick = {
                            viewModel.changeIsDialogVisible(true)
                        }) {
                            Icon(
                                imageVector = Icons.Default.Delete,
                                contentDescription = "Delete password"
                            )
                        }
                        IconButton(onClick = {
                            viewModel.toggleEditMode(id, name, login)
                        }) {
                            Icon(
                                imageVector = if (isReadOnly) Icons.Default.Edit else Icons.Default.Done,
                                contentDescription = "Edit password"
                            )
                        }
                    }
                )
            }
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(
                        vertical = it.calculateTopPadding() + 15.dp,
                        horizontal = 10.dp
                    ),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    readOnly = isReadOnly,
                    label = { Text(text = "Name") }
                )
                Spacer(modifier = Modifier.padding(10.dp))
                TextField(
                    value = login,
                    onValueChange = { login = it },
                    readOnly = isReadOnly,
                    label = { Text(text = "Login") }
                )
                Spacer(modifier = Modifier.padding(10.dp))

                Box(
                    modifier = Modifier.clickable(
                        onClick = {
                            if (!isPasswordVisible) {
                                if (authenticationManager.canAuthenticate(context)) {
                                    authenticationManager.authenticate(
                                        activity = context as FragmentActivity,
                                        cipher = viewModel.getDecryptionCipher(passwordObj.iv),
                                        callback = object :
                                            BiometricPrompt.AuthenticationCallback() {
                                            override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
                                                super.onAuthenticationSucceeded(result)
                                                decryptedPassword =
                                                    viewModel.getDecryptedPassword(
                                                        passwordObj
                                                    )
                                                isPasswordVisible = true
                                            }

                                            override fun onAuthenticationError(
                                                errorCode: Int,
                                                errString: CharSequence
                                            ) {
                                                super.onAuthenticationError(
                                                    errorCode,
                                                    errString
                                                )
                                                toastProvider.show(errString)
                                            }

                                            override fun onAuthenticationFailed() {
                                                super.onAuthenticationFailed()
                                                toastProvider.show("Authentication failed")
                                            }
                                        }
                                    )
                                } else {
                                    toastProvider.show("No biometric authentication available for your device")
                                }
                            }
                        }
                    )
                ) {
                    if (!isPasswordVisible) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_visibility),
                            contentDescription = "Password",
                        )
                    } else {
                        TextField(
                            value = decryptedPassword,
                            onValueChange = { decryptedPassword = it },
                            readOnly = true,
                            label = { Text(text = "Password") }
                        )
                    }
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Composable
fun DeletePasswordDialog(
    onDismiss: () -> Unit,
    onSave: () -> Unit
) {

    Dialog(
        onDismissRequest = onDismiss
    ) {
        Surface(
            modifier = Modifier
                .wrapContentWidth()
                .wrapContentHeight(),
            shape = MaterialTheme.shapes.large
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "Are you sure you want to remove the password?",
                    style = MaterialTheme.typography.titleMedium
                )
                Spacer(modifier = Modifier.padding(20.dp))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.End
                ) {
                    TextButton(onClick = onDismiss) {
                        Text(text = "Dismiss")
                    }
                    TextButton(onClick = onSave) {
                        Text(text = "Yes")
                    }
                }
            }
        }
    }
}