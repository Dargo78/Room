package com.application.room.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [DaftarBelanja::class], version = 1)
abstract class HistoryBelanjaDB: RoomDatabase() {
    abstract fun historyBelanjaDAO(): HistoryBelanjaDAO

    companion object {
        @Volatile
        private var INSTANCE: HistoryBelanjaDB? = null

        @JvmStatic
        fun getDatabase(context: Context): HistoryBelanjaDB {
            if (INSTANCE == null) {
                synchronized(HistoryBelanjaDB::class.java) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        HistoryBelanjaDB::class.java, "historyBelanja_db"
                    )
                        .allowMainThreadQueries()
                        .build()
                }
            }
            return INSTANCE as HistoryBelanjaDB
        }
    }
}