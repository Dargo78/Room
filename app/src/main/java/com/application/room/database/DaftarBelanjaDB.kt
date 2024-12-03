package com.application.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DaftarBelanja::class], version = 1)
abstract class DaftarBelanjaDB : RoomDatabase() {
    abstract fun daftarBelanjaDAO(): DaftarBelanjaDAO

    companion object {
        @Volatile
        private var INSTANCE: DaftarBelanjaDB? = null

        @JvmStatic
        fun getDatabase(context: Context): DaftarBelanjaDB {
            if (INSTANCE == null) {
                synchronized(DaftarBelanjaDB::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        DaftarBelanjaDB::class.java, "daftarBelanja_db"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as DaftarBelanjaDB
        }
    }
}