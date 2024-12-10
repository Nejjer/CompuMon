package com.devofy.compumon.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devofy.compumon.view.components.LoadingIndicator
import com.devofy.compumon.viewmodels.PCStatusViewModel


@Composable
fun PCStatusScreen(viewModel: PCStatusViewModel) {
    val pcStatus by viewModel.pcStatus

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            if (pcStatus != null) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // Указываем два столбца
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp), // Отступы по горизонтали
                    verticalArrangement = Arrangement.spacedBy(24.dp)   // Отступы по вертикали
                ) {
                    item() {
                        LoadingIndicator(
                            title = "CPU",
                            currentValue = pcStatus!!.cpu.load,
                            maxValue = 100.0,
                            unit = "%"
                        )
                    }
                    item() {
                        LoadingIndicator(
                            title = "Temp",
                            currentValue = pcStatus!!.cpu.temperature,
                            maxValue = 100.0,
                            unit = "°C"
                        )
                    }
                    item() {
                        LoadingIndicator(
                            title = "FanSpeed",
                            currentValue = pcStatus!!.cpu.fanSpeed.toDouble(),
                            maxValue = 1800.0,
                            unit = "rpm"
                        )
                    }
                    item() {}
                    item() {
                        LoadingIndicator(
                            title = "GPU",
                            currentValue = pcStatus!!.gpu.load,
                            maxValue = 100.0,
                            unit = "%"
                        )
                    }
                    item() {
                        LoadingIndicator(
                            title = "Gpu temp",
                            currentValue = pcStatus!!.gpu.temperature,
                            maxValue = 100.0,
                            unit = "°C"
                        )
                    }
                    item() {
                        LoadingIndicator(
                            title = "Gpu memory",
                            currentValue = pcStatus!!.gpu.memory.used,
                            maxValue = pcStatus!!.gpu.memory.total,
                            unit = "Gb"
                        )
                    }
                    item() {
                        LoadingIndicator(
                            title = "Memory",
                            currentValue = pcStatus!!.ram.used,
                            maxValue = pcStatus!!.ram.total,
                            unit = "Gb"
                        )
                    }
                }
            } else {
                Text("Loading...", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}