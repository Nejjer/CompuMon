package com.example.compumon.view

import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.compumon.MainActivity
import com.example.compumon.viewmodels.SettingsViewModel

@Composable
fun SettingsScreen(viewModel: SettingsViewModel = viewModel()) {
    val context = LocalContext.current

    // Загружаем сохраненные адреса при запуске экрана
    LaunchedEffect(true) {
        viewModel.loadServerAddresses()
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = "Настройки серверов", style = MaterialTheme.typography.titleMedium)

        Spacer(modifier = Modifier.height(24.dp))

        // Поля для ввода адресов серверов
        OutlinedTextField(
            value = viewModel.pcServer.value,
            onValueChange = { newVal -> viewModel.pcServer.value = newVal },
            label = { Text("Сервер 1") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = viewModel.remoteServer.value,
            onValueChange = { newVal -> viewModel.remoteServer.value = newVal },
            label = { Text("Сервер 2") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        OutlinedTextField(
            value = viewModel.selfHostedServer.value,
            onValueChange = { newVal -> viewModel.selfHostedServer.value = newVal },
            label = { Text("Сервер 3") },
            modifier = Modifier.fillMaxWidth(),
            keyboardOptions = KeyboardOptions.Default.copy(keyboardType = KeyboardType.Text),
            singleLine = true
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Кнопка сохранения и перехода на главный экран
        Button(
            onClick = {
                viewModel.saveServerAddresses()
                Toast.makeText(
                    context,
                    "Адреса серверов сохранены",
                    Toast.LENGTH_SHORT
                ).show()

                context.startActivity(
                    Intent(
                        context,
                        MainActivity::class.java
                    )
                ) // Возвращаемся на главный экран
            },
            modifier = Modifier.fillMaxWidth(0.6f)
        ) {
            Text(text = "Сохранить и вернуться")
        }
    }
}


@Preview(showBackground = true)
@Composable
fun PreviewSettingsScreen() {
    SettingsScreen()
}
