package com.devofy.compumon.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.devofy.compumon.viewmodels.LaunchViewModel

@Composable
fun WelcomeScreen() {
    var currentStep by remember { mutableStateOf(0) }
    val context = LocalContext.current
    val launchViewModel: LaunchViewModel = viewModel()

    Box(modifier = Modifier.fillMaxSize()) {
        // Контент в верхней части экрана
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .padding(top = 24.dp)
                .align(Alignment.TopCenter),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when (currentStep) {
                0 -> WelcomeStep(
                    title = "Добро пожаловать!",
                    description = "Это приложение поможет вам следить за состоянием ваших компьютеров в реальном времени."
                )

                1 -> WelcomeStep(
                    title = "Установка сервера",
                    description = "На каждом компьютере необходимо установить и запустить сервер для передачи данных."
                )

                2 -> WelcomeStep(
                    title = "Настройки",
                    description = "После установки серверов введите их адреса в настройках приложения.",
                    showSettings = true
                )
            }
        }

        // Кнопка "Далее" прижата к нижней части экрана
        if (currentStep < 2) {
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.BottomCenter)
                    .padding(16.dp),
                horizontalArrangement = Arrangement.Center
            ) {
                Button(
                    onClick = { currentStep++ },
                    modifier = Modifier.fillMaxWidth(0.5f) // Кнопка занимает 50% ширины
                ) {
                    Text(text = "Далее")
                }
            }
        }
    }
}

@Composable
fun WelcomeStep(
    title: String,
    description: String,
    showSettings: Boolean = false
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = title,
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        Text(
            text = description,
            fontSize = 16.sp,
            modifier = Modifier.padding(bottom = 16.dp)
        )
        if (showSettings) SettingsScreen()
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewWelcomeScreen() {
    WelcomeScreen()
}
