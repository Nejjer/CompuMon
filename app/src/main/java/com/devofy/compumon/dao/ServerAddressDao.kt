package com.devofy.compumon.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.devofy.compumon.enities.ServersAddress

@Dao
interface ServerAddressDao {

    @Insert
    suspend fun insert(serverAddress: ServersAddress)

    @Query("SELECT * FROM server_addresses ORDER BY id DESC LIMIT 1")
    suspend fun getServerAddresses(): ServersAddress?
}
