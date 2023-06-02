package com.example.passwordmanager.ui.main

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.filled.KeyboardArrowRight
import androidx.compose.material3.Card
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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.example.passwordmanager.repository.model.Password
import com.example.passwordmanager.ui.theme.PasswordManagerTheme
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalFoundationApi::class)
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
    val passwords by viewModel.passwords.collectAsState(initial = emptyMap())

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
                if (name.isNotEmpty() and login.isNotEmpty() and password.isNotEmpty()) {
                    viewModel.addPassword()
                    viewModel.changeIsDialogVisible(false)
                }
            }
        )

    Scaffold(
        topBar = {
            TopAppBar(title = {
                Text(
                    text = "PasswordManager",
                    style = MaterialTheme.typography.headlineMedium,
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
                start = 12.dp,
                top = it.calculateTopPadding(),
                end = 12.dp,
                bottom = it.calculateBottomPadding()
            )
        ) {
            passwords.forEach { (header, passwordsForHeader) ->
                stickyHeader {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(MaterialTheme.colorScheme.background)
                    ) {
                        Text(
                            text = header,
                            style = MaterialTheme.typography.labelLarge
                        )
                    }
                }
                items(passwordsForHeader) { password ->
                    PasswordListItem(password, navigateToPasswordDetails)
                }
            }
        }
    }
}

@Composable
fun PasswordListItem(password: Password?, navigateToDetails: (Int) -> Unit) {
    Card(
        modifier = Modifier
            .padding(vertical = 6.dp)
            .fillMaxWidth()
            .clickable {
                password?.let {
                    navigateToDetails(password.id)
                }
            }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 10.dp),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                password?.let {
                    Text(text = password.name, style = MaterialTheme.typography.titleMedium)
                    Text(text = password.login, style = MaterialTheme.typography.bodyLarge)
                }
            }
            Icon(
                imageVector = Icons.Default.KeyboardArrowRight,
                contentDescription = "Go to details"
            )
        }
    }
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
                Text(
                    text = "Add new password",
                    style = MaterialTheme.typography.titleMedium
                )
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