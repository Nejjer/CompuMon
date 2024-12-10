package com.devofy.compumon.view

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.devofy.compumon.SettingActivity
import com.devofy.compumon.view.components.LoadingIndicator
import com.devofy.compumon.viewmodels.IndicatorData
import com.devofy.compumon.viewmodels.PCStatusViewModel


@Composable
fun PCStatusScreen(viewModel: PCStatusViewModel) {
    val context = LocalContext.current

    val pcStatus by viewModel.pcStatus

    pcStatus?.let { status -> // Проверяем на null и создаем локальную переменную
        // Список для рендеринга LoadingIndicator
        val indicators = mutableListOf<IndicatorData>()

        // Добавление данных CPU
        indicators.add(IndicatorData("CPU", status.cpu.load, 100.0, "%"))
        indicators.add(IndicatorData("Temp", status.cpu.temperature, 100.0, "°C"))
        indicators.add(IndicatorData("FanSpeed", status.cpu.fanSpeed.toDouble(), 1800.0, "rpm"))

        // Добавление данных GPU, если оно не null
        status.gpu?.let { gpu ->
            indicators.add(IndicatorData("GPU", gpu.load, 100.0, "%"))
            indicators.add(IndicatorData("Gpu temp", gpu.temperature, 100.0, "°C"))
            indicators.add(IndicatorData("Gpu memory", gpu.memory.used, gpu.memory.total, "Gb"))
        }

        // Добавление данных RAM
        indicators.add(IndicatorData("Memory", status.ram.used, status.ram.total, "Gb"))

        Surface(modifier = Modifier.fillMaxSize()) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // Указываем два столбца
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp), // Отступы по горизонтали
                    verticalArrangement = Arrangement.spacedBy(24.dp),   // Отступы по вертикали
                    content = {
                        items(indicators) { indicator ->
                            LoadingIndicator(
                                title = indicator.title,
                                currentValue = indicator.currentValue,
                                maxValue = indicator.maxValue,
                                unit = indicator.unit
                            )
                        }
                    }
                )
            }
        }

    } ?: run {
        if (viewModel.isServerApiUrlEmpty) {
            Column(
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                Text(
                    text = "Не задан адрес сервера",
                    modifier = Modifier.padding(8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
                Button(onClick = {
                    context.startActivity(
                        Intent(
                            context,
                            SettingActivity::class.java
                        )
                    )
                }) { Text("Перейти в настройки") }
            }
        } else {
            CircularProgressIndicator(
                modifier = Modifier.size(50.dp),
                strokeWidth = 4.dp
            )
        }
    }


}
