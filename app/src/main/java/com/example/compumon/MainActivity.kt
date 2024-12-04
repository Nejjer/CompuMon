package com.example.compumon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compumon.ui.theme.CompuMonTheme
import com.example.compumon.view.SettingsScreen
import com.example.compumon.view.WelcomeScreen


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()
        setContent {
            CompuMonTheme {
                val navController = rememberNavController()

                NavHost(navController = navController, startDestination = "welcome") {
                    composable("welcome") {
                        WelcomeScreen(navController = navController)
                    }
                    composable("settings") {
                        SettingsScreen(navController = navController) // Экран настроек
                    }
                }
            }
        }
    }
}
