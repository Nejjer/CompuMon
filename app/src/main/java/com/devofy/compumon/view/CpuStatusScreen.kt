package com.devofy.compumon.view

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.devofy.compumon.viewmodels.CpuStatusViewModel


@Composable
fun CpuStatusScreen(viewModel: CpuStatusViewModel) {
    val cpuStatus by viewModel.cpuStatus

    Surface(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.Center
        ) {
            if (cpuStatus != null) {
                LazyVerticalGrid(
                    columns = GridCells.Fixed(2), // Указываем два столбца
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp),
                    horizontalArrangement = Arrangement.spacedBy(16.dp), // Отступы по горизонтали
                    verticalArrangement = Arrangement.spacedBy(24.dp)   // Отступы по вертикали
                ) {
                    item() {
                        ProgressWithTitle(
                            title = "CPU",
                            currentValue = cpuStatus!!.cpu.load,
                            maxValue = 100.0,
                            unit = "%"
                        )
                    }
                    item() {
                        ProgressWithTitle(
                            title = "Temp",
                            currentValue = cpuStatus!!.cpu.temperature,
                            maxValue = 100.0,
                            unit = "°C"
                        )
                    }
                    item() {
                        ProgressWithTitle(
                            title = "FanSpeed",
                            currentValue = cpuStatus!!.cpu.fanSpeed.toDouble(),
                            maxValue = 1800.0,
                            unit = "rpm"
                        )
                    }
                    item() {}
                    item() {
                        ProgressWithTitle(
                            title = "GPU",
                            currentValue = cpuStatus!!.gpu.load,
                            maxValue = 100.0,
                            unit = "%"
                        )
                    }
                    item() {
                        ProgressWithTitle(
                            title = "Gpu temp",
                            currentValue = cpuStatus!!.gpu.temperature,
                            maxValue = 100.0,
                            unit = "°C"
                        )
                    }
                    item() {
                        ProgressWithTitle(
                            title = "Gpu memory",
                            currentValue = cpuStatus!!.gpu.memory.used,
                            maxValue = cpuStatus!!.gpu.memory.total,
                            unit = "Gb"
                        )
                    }
                    item() {
                        ProgressWithTitle(
                            title = "Memory",
                            currentValue = cpuStatus!!.ram.used,
                            maxValue = cpuStatus!!.ram.total,
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