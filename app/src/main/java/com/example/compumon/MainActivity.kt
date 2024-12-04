package com.example.compumon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.compumon.ui.theme.CompuMonTheme
import com.example.compumon.view.CpuStatusScreen
import com.example.compumon.view.SettingsScreen
import com.example.compumon.view.WelcomeScreen
import com.example.compumon.viewmodels.CpuStatusViewModel


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display
        enableEdgeToEdge()

        setContent {
            CompuMonApp()
        }
    }
}

@Composable
fun CompuMonApp() {
    CompuMonTheme {
        val navController = rememberNavController()

        NavHost(navController = navController, startDestination = Screen.Welcome.route) {
            composable(Screen.Welcome.route) {
                WelcomeScreen(navController = navController)
            }
            composable(Screen.Settings.route) {
                SettingsScreen(navController = navController)
            }
            composable(Screen.Main.route) {
                val viewModel: CpuStatusViewModel = CpuStatusViewModel()
                CpuStatusScreen(viewModel)
            }
        }
    }
}

// Define screens as a sealed class for better type safety
sealed class Screen(val route: String) {
    object Welcome : Screen("welcome")
    object Settings : Screen("settings")
    object Main : Screen("main")
}
//TODO Надо будет переделать на три Активити. Велком, Майн и активити настроек. Также спросить разницу между разделением на активити и тем что щас