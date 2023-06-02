package com.example.passwordmanager.ui.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Card
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.passwordmanager.repository.model.Password
import com.example.passwordmanager.ui.theme.PasswordManagerTheme
import org.koin.androidx.compose.koinViewModel

@ExperimentalMaterial3Api
@Composable
fun PasswordList(
    viewModel: PasswordListViewModel = koinViewModel(),
    navigateToPasswordDetails: (Int) -> Unit
) {
    val isDialogVisible by remember { viewModel.isDialogVisible }
    var name by remember { viewModel.name }
    var login by remember { viewModel.login }
    var password by remember { viewModel.password }
    val passwords by viewModel.passwords.collectAsState(initial = emptyList())

    LaunchedEffect(true) {
        viewModel.getPasswords()
    }

    if (isDialogVisible)
        AddPasswordDialog(
            name = name,
            login = login,
            password = password,
            onNameChange = { name = it },
            onLoginChange = { login = it },
            onPasswordChange = { password = it },
            onDismiss = {
                viewModel.changeIsDialogVisible(false)
            },
            onSave = {
                viewModel.addPassword()
                viewModel.changeIsDialogVisible(false)
            }
        )

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "PasswordManager",
                    style = MaterialTheme.typography.titleLarge
                )
            })
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { viewModel.changeIsDialogVisible(true) }
            ) { Icon(imageVector = Icons.Default.Add, contentDescription = "Add new password") }
        }
    ) {
        LazyColumn(
            modifier = Modifier.padding(
                start = 10.dp,
                top = it.calculateTopPadding(),
                end = 10.dp,
                bottom = it.calculateBottomPadding()
            )
        ) {
            items(passwords) { password ->
                PasswordListItem(password, navigateToPasswordDetails)
            }
        }
    }
}

@Composable
fun PasswordListItem(password: Password?, navigateToDetails: (Int) -> Unit) {

//    val context = LocalContext.current
//    val authenticationManager = get<AuthenticationManager>()
//    val toastProvider = get<ToastProvider>()

    // var isPasswordVisible by remember { mutableStateOf(false) }
    // var decryptedPassword by remember { mutableStateOf("") }

    Card(
        modifier = Modifier
            .padding(vertical = 4.dp)
            .fillMaxWidth()
            .clickable {
                password?.let {
                    navigateToDetails(password.id)
                }
            }
        //                {
//                    if (!isPasswordVisible) {
//                        if (authenticationManager.canAuthenticate(context)) {
//                            authenticationManager.authenticate(
//                                activity = context as FragmentActivity,
//                                cipher = cipher,
//                                callback = object : BiometricPrompt.AuthenticationCallback() {
//                                    override fun onAuthenticationSucceeded(result: BiometricPrompt.AuthenticationResult) {
//                                        super.onAuthenticationSucceeded(result)
//                                        decryptedPassword = onAuthenticationSucceeded()
//                                        isPasswordVisible = true
//                                    }
//
//                                    override fun onAuthenticationError(
//                                        errorCode: Int,
//                                        errString: CharSequence
//                                    ) {
//                                        super.onAuthenticationError(errorCode, errString)
//                                        toastProvider.show(errString)
//                                    }
//
//                                    override fun onAuthenticationFailed() {
//                                        super.onAuthenticationFailed()
//                                        toastProvider.show("Authentication failed")
//                                    }
//                                }
//                            )
//                        } else {
//                            toastProvider.show("No biometric authentication available for your device")
//                        }
//                    }
//                }

    ) {
        Column(
            modifier = Modifier.padding(horizontal = 10.dp)
        ) {
            password?.let {
                PasswordListItemText(text = password.name)
                Divider()
                PasswordListItemText(text = password.login)
            }
//            Box(
//                modifier = Modifier.height(30.dp)
//            ) {
//                //   if (isPasswordVisible) PasswordListItemText(text = decryptedPassword)
//                // else
//                Icon(
//                    painter = painterResource(id = R.drawable.ic_visibility),
//                    contentDescription = "Password",
//                )
//            }
        }
    }
}

@Composable
fun PasswordListItemText(text: String) {
    Text(
        text = text,
        modifier = Modifier.padding(vertical = 5.dp),
        style = MaterialTheme.typography.bodyLarge
    )
}

@ExperimentalMaterial3Api
@Composable
fun AddPasswordDialog(
    name: String,
    login: String,
    password: String,
    onNameChange: (String) -> Unit,
    onLoginChange: (String) -> Unit,
    onPasswordChange: (String) -> Unit,
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
                Text(text = "Add new password")
                Spacer(modifier = Modifier.padding(10.dp))
                TextField(
                    value = name,
                    onValueChange = onNameChange,
                    label = { Text(text = "Name") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.padding(10.dp))
                TextField(
                    value = login,
                    onValueChange = onLoginChange,
                    label = { Text(text = "Login") },
                    singleLine = true
                )
                Spacer(modifier = Modifier.padding(10.dp))
                TextField(
                    value = password,
                    onValueChange = onPasswordChange,
                    label = { Text(text = "Password") },
                    singleLine = true
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
                        Text(text = "Save")
                    }
                }
            }
        }
    }
}

@ExperimentalMaterial3Api
@Preview(showBackground = true)
@Composable
fun PasswordListScreenPreview() {
    PasswordManagerTheme {
        PasswordList(
            navigateToPasswordDetails = {}
        )
    }
}