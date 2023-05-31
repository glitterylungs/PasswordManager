package com.example.passwordmanager.ui.details

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
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordDetails(
    viewModel: PasswordDetailsViewModel = koinViewModel(),
    id: Int,
    navigateToPreviousScreen: () -> Unit
) {

    var name by remember { viewModel.name }
    var login by remember { viewModel.login }
    var password by remember { viewModel.password }

    LaunchedEffect(true) {
        viewModel.getDataById(id)
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Details") },
                navigationIcon = {
                    IconButton(onClick = navigateToPreviousScreen) {
                        Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Return")
                    }
                },
                actions = {
                    IconButton(onClick = {/* delete*/ }) {
                        Icon(Icons.Default.Delete, null)
                    }
                    IconButton(onClick = {/* edit*/ }) {
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
                readOnly = true
            )
            TextField(
                value = login,
                onValueChange = { login = it },
                readOnly = true
            )
            TextField(
                value = password,
                onValueChange = { password = it },
                readOnly = true
            )
        }

    }

}