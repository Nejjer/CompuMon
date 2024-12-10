package com.devofy.compumon.viewmodels

import android.app.Application
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.devofy.compumon.databases.AppDatabase
import com.devofy.compumon.enities.ServersAddress
import kotlinx.coroutines.launch

class SettingsViewModel(application: Application) : AndroidViewModel(application) {

    private val serverAddressDao = AppDatabase.getDatabase(application).serverAddressDao()

    var pcServer = mutableStateOf("")
    var remoteServer = mutableStateOf("")
    var selfHostedServer = mutableStateOf("")

    fun saveServerAddresses() {
        val serverAddress = ServersAddress(
            pcServer = pcServer.value,
            remoteServer = remoteServer.value,
            selfHostedServer = selfHostedServer.value
        )
        viewModelScope.launch {
            serverAddressDao.insert(serverAddress)
        }
    }

    fun loadServerAddresses() {
        viewModelScope.launch {
            val savedAddresses = serverAddressDao.getServerAddresses()
            savedAddresses?.let {
                pcServer.value = it.pcServer
                remoteServer.value = it.remoteServer
                selfHostedServer.value = it.selfHostedServer
            }
        }
    }

    fun getServerAddresses() {
        viewModelScope.launch {
            serverAddressDao.getServerAddresses()
        }
    }
}
