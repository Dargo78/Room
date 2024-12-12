package com.application.room

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
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

        var _btnTambah = findViewById<Button>(R.id.btnTambah)
        var _btnUpdate = findViewById<Button>(R.id.btnUpdate)

        _btnTambah.setOnClickListener {
            // Untuk membatalkan update data apabila terjadi kegagalan (kyk db trans commit)
            CoroutineScope(Dispatchers.IO).async {
                DB.daftarBelanjaDAO().insert(
                    DaftarBelanja(
                        tanggal = tanggal,
                        item = _etItem.text.toString(),
                        jumlah = _etJumlah.text.toString()
                    )
                )
            }
            finish()
        }

        var iID: Int = 0
        var iAddEdit: Int = 0

        iID = intent.getIntExtra("id", 0)
        iAddEdit = intent.getIntExtra("addEdit", 0)

        if (iAddEdit == 0) {
            _btnTambah.visibility = View.VISIBLE
            _btnUpdate.visibility = View.GONE
            _etItem.isEnabled = true
        } else {
            _btnTambah.visibility = View.GONE
            _btnUpdate.visibility = View.VISIBLE
            _etItem.isEnabled = false

            // Untuk membatalkan update data apabila terjadi kegagalan (kyk db trans commit)
            CoroutineScope(Dispatchers.IO).async {
                val item = DB.daftarBelanjaDAO().getItem(iID)
                _etItem.setText(item.item)
                _etJumlah.setText(item.jumlah)
            }
        }

        _btnUpdate.setOnClickListener {
            // Untuk membatalkan update data apabila terjadi kegagalan (kyk db trans commit)
            CoroutineScope(Dispatchers.IO).async {
                DB.daftarBelanjaDAO().update(
                    isi_tanggal = tanggal,
                    isi_item = _etItem.text.toString(),
                    isi_jumlah = _etJumlah.text.toString(),
                    pilihId = iID
                )
            }
            finish()
        }
    }
}