package com.example.compumon.viewmodels

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException


data class PCStatus(
    val cpu: CpuStatus2,
    val gpu: GpuStatus,
    val ram: MemoryStatus
)

data class CpuStatus2(
    val load: Double,
    val temperature: Double,
    val fanSpeed: Int
)

data class GpuStatus(
    val load: Double,
    val temperature: Double,
    val memory: MemoryStatus,
    val fanSpeed: Int
)

data class MemoryStatus(
    val total: Double,
    val used: Double
)

class CpuStatusViewModel : ViewModel() {

    private val client by lazy { OkHttpClient() } // Lazy initialization for OkHttpClient

    // State to hold the CPU status data, initially null
    private val _cpuStatus = mutableStateOf<PCStatus?>(null)
    val cpuStatus: State<PCStatus?> get() = _cpuStatus

    // Polling interval in milliseconds
    companion object {
        const val POLLING_INTERVAL_MS = 500L
        const val API_URL = "http://192.168.1.164:5000/api/getPcStatus"
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

    // Parsing the JSON response into a PCStatus object using Gson
    private fun parseCpuStatus(json: String): PCStatus? {
        return try {
            val gson = Gson()
            gson.fromJson(json, PCStatus::class.java)
        } catch (e: JsonSyntaxException) {
            e.printStackTrace()
            null
        }
    }
}
