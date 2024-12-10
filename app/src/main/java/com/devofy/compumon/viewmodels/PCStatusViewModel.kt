package com.devofy.compumon.viewmodels

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

class PCStatusViewModel(private val apiUrl: String, private val pollingInterval: Long) :
    ViewModel() {

    private val client by lazy { OkHttpClient() } // Lazy initialization for OkHttpClient

    // State to hold the CPU status data, initially null
    private val _cpuStatus = mutableStateOf<PCStatus?>(null)
    val pcStatus: State<PCStatus?> get() = _cpuStatus

    init {
        startPolling()
    }

    // Starts the periodic polling of the API
    private fun startPolling() {
        viewModelScope.launch {
            while (true) {
                fetchCpuStatus()
                delay(pollingInterval)
            }
        }
    }

    // Fetches the CPU status from the API
    private suspend fun fetchCpuStatus() {
        if (apiUrl.isEmpty()) return
        try {
            val response = executeRequest("$apiUrl/api/getPcStatus")
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
