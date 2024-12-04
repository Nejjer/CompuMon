package com.example.compumon.view

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.compumon.viewmodels.CpuStatusViewModel


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
                Text("CPU Status:", style = MaterialTheme.typography.titleMedium)
                Spacer(modifier = Modifier.height(8.dp))
                Text("Average Temp: ${cpuStatus!!.averageTemp}°C")
                Text("CPU Usage: ${cpuStatus!!.cpuUsage}%")
                Text("Fan Speed: ${cpuStatus!!.fanSpeed} RPM")
                Text("Total Memory: ${cpuStatus!!.totalMemory} GB")
                Text("Used Memory: ${cpuStatus!!.usedMemory} GB")
            } else {
                Text("Loading...", style = MaterialTheme.typography.bodyMedium)
            }
        }
    }
}