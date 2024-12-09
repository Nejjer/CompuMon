package com.example.compumon.viewmodels

import android.app.Application
import android.content.SharedPreferences
import androidx.lifecycle.AndroidViewModel

class LaunchViewModel(application: Application) :
    AndroidViewModel(application) {

    private val sharedPreferences: SharedPreferences =
        application.getSharedPreferences(KEY_PREF, Application.MODE_PRIVATE)

    companion object {
        private const val KEY_FIRST_LAUNCH = "is_first_launch1"
        private const val KEY_PREF = "app_prefs"
    }

    // Проверяет, первый ли запуск приложения
    fun isFirstLaunch() = sharedPreferences.getBoolean(KEY_FIRST_LAUNCH, true)

    // Устанавливает флаг завершения первого запуска
    fun markFirstLaunchComplete() {
        sharedPreferences.edit().putBoolean(KEY_FIRST_LAUNCH, false).apply()
    }
}