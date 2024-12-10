package com.devofy.compumon.databases

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.devofy.compumon.dao.ServerAddressDao
import com.devofy.compumon.enities.ServersAddress

@Database(entities = [ServersAddress::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun serverAddressDao(): ServerAddressDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "server_addresses_database"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}
