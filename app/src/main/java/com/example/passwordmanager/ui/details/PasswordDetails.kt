package com.example.passwordmanager.ui.details

import androidx.biometric.BiometricPrompt
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
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

    LaunchedEffect(true) {
        viewModel.getDataById(id)
    }

    passwordObject?.let { passwordObj ->
        Scaffold(
            topBar = {
                TopAppBar(
                    title = { Text(text = "Details") },
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
                            viewModel.deletePassword(id)
                            navigateToPreviousScreen()
                        }) {
                            Icon(Icons.Default.Delete, null)
                        }
                        IconButton(onClick = {
                            viewModel.toggleEditMode(id)
                        }) {
                            Icon(Icons.Default.Edit, null)
                        }
                    }
                )
            }
        ) {
            Column(modifier = Modifier.padding(it)) {
                TextField(
                    value = name,
                    onValueChange = { name = it },
                    readOnly = isReadOnly
                )
                TextField(
                    value = login,
                    onValueChange = { login = it },
                    readOnly = isReadOnly
                )
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
                            readOnly = isReadOnly,
                        )
                    }
                }
            }
            }
        }}
