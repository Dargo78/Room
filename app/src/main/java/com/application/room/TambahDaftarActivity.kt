package com.application.room

import android.os.Bundle
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.application.room.database.DaftarBelanja
import com.application.room.database.DaftarBelanjaDB
import com.application.room.helper.DateHelper.getCurrentDate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class TambahDaftarActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_tambah_daftar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        var DB = DaftarBelanjaDB.getDatabase(this)
        var tanggal = getCurrentDate()


        var _etItem = findViewById<EditText>(R.id.etItem)
        var _etJumlah = findViewById<EditText>(R.id.etJumlah)
        CoroutineScope(Dispatchers.IO).async {
            DB.daftarBelanjaDAO().insert(
                DaftarBelanja(
                    tanggal = tanggal,
                    item = _etItem.text.toString(),
                    jumlah = _etJumlah.text.toString()
                )
            )
        }
    }
}