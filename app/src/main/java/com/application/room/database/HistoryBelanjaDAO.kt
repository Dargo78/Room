package com.application.room.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface HistoryBelanjaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(daftar: DaftarBelanja)

    @Query("SELECT * FROM DaftarBelanja ORDER BY id ASC")
    fun selectAll() : MutableList<DaftarBelanja>
}