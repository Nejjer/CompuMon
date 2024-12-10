package com.devofy.compumon

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.devofy.compumon.view.CpuStatusScreen
import com.devofy.compumon.viewmodels.CpuStatusViewModel


class MainActivity : ComponentActivity() {
    val viewModel: CpuStatusViewModel = CpuStatusViewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Enable edge-to-edge display
        enableEdgeToEdge()

        setContent {
            CpuStatusScreen(viewModel)
        }
    }
}

