package com.example.compumon

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.viewModels
import com.example.compumon.viewmodels.LaunchViewModel

class LaunchActivity : ComponentActivity() {

    private val launchViewModel: LaunchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        this.navigateToNextActivity()
    }

    private fun navigateToNextActivity() {
        if (launchViewModel.isFirstLaunch()) {
            navigateToActivity(WelcomeActivity::class.java)
        } else {
            navigateToActivity(MainActivity::class.java)
        }
        finish() // Завершаем текущую Activity, чтобы пользователь не мог вернуться назад
    }


    private fun navigateToActivity(target: Class<*>) {
        val intent = Intent(this, target)
        startActivity(intent)
    }

}