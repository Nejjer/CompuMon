package com.example.compumon.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.json.JSONObject
import java.io.IOException

// Data class representing the CPU status
data class CpuStatus(
    val averageTemp: Double, // The average temperature of the CPU in degrees Celsius
    val cpuUsage: Double,    // The percentage of CPU usage
    val fanSpeed: Int,       // The speed of the fan in RPM (Revolutions Per Minute)
    val totalMemory: Double, // The total available memory in GB
    val usedMemory: Double   // The memory currently in use in GB
)

class CpuStatusViewModel : ViewModel() {

    private val client by lazy { OkHttpClient() } // Lazy initialization for OkHttpClient

    // State to hold the CPU status data, initially null
    private val _cpuStatus = mutableStateOf<CpuStatus?>(null)
    val cpuStatus: State<CpuStatus?> get() = _cpuStatus

    // Polling interval in milliseconds
    private companion object {
        const val POLLING_INTERVAL_MS = 500L
        const val API_URL = "http://192.168.1.119:5000/api/getCpuStatus"
    }

    init {
        startPolling()
    }

    // Starts the periodic polling of the API
    private fun startPolling() {
        viewModelScope.launch {
            while (true) {
                fetchCpuStatus()
                delay(POLLING_INTERVAL_MS)
            }
        }
    }

    // Fetches the CPU status from the API
    private suspend fun fetchCpuStatus() {
        try {
            val response = executeRequest(API_URL)
            response?.let { json ->
                val cpuStatus = parseCpuStatus(json)
                _cpuStatus.value = cpuStatus
            }
        } catch (e: IOException) {
            // Log or handle the error appropriately (e.g., send to a logging framework)
            e.printStackTrace()
        }
    }

    // Executes an HTTP GET request and returns the response as a String
    private suspend fun executeRequest(url: String): String? =
        withContext(Dispatchers.IO) {
            try {
                val request = Request.Builder().url(url).build()
                client.newCall(request).execute().use { response ->
                    if (response.isSuccessful) {
                        response.body?.string()
                    } else {
                        null // Handle unsuccessful response
                    }
                }
            } catch (e: IOException) {
                e.printStackTrace() // Log or handle the error
                null
            }
        }

    // Parses the JSON response into a CpuStatus object
    private fun parseCpuStatus(json: String): CpuStatus {
        val jsonObject = JSONObject(json)
        return CpuStatus(
            averageTemp = jsonObject.getDouble("averageTemp"),
            cpuUsage = jsonObject.getDouble("cpuUsage"),
            fanSpeed = jsonObject.getInt("fanSpeed"),
            totalMemory = jsonObject.getDouble("totalMemory"),
            usedMemory = jsonObject.getDouble("usedMemory")
        )
    }
}
