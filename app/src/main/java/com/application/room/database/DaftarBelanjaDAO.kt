package com.application.room.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface DaftarBelanjaDAO {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(daftar: DaftarBelanja)

    @Query("UPDATE DaftarBelanja SET tanggal=:isi_tanggal, item=:isi_item, jumlah=:isi_jumlah WHERE id=:pilihId")
    fun update(isi_tanggal: String, isi_item: String, isi_jumlah: String, pilihId: Int)

    @Delete
    fun delete(daftar: DaftarBelanja)

    @Query("SELECT * FROM DaftarBelanja ORDER BY id ASC")
    fun selectAll() : MutableList<DaftarBelanja>
}