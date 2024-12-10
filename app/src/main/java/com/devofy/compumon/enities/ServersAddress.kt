package com.devofy.compumon.enities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "server_addresses")
data class ServersAddress(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    val pcServer: String,
    val selfHostedServer: String,
    val remoteServer: String
)

