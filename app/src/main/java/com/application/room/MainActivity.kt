package com.application.room

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.room.database.DaftarBelanja
import com.application.room.database.DaftarBelanjaDB
import com.application.room.database.HistoryBelanja
import com.application.room.database.HistoryBelanjaDAO
import com.application.room.database.HistoryBelanjaDB
import com.google.android.material.floatingactionbutton.FloatingActionButton
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.withContext

class MainActivity : AppCompatActivity() {
    private lateinit var DB: DaftarBelanjaDB
    private lateinit var adapterDaftar: AdapterDaftar
    private var arDaftar: MutableList<DaftarBelanja> = mutableListOf()

    private lateinit var HistoryDB: HistoryBelanjaDB
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        DB = DaftarBelanjaDB.getDatabase(this)
        HistoryDB = HistoryBelanjaDB.getDatabase(this)
        adapterDaftar = AdapterDaftar(arDaftar)

        var _fabAdd = findViewById<FloatingActionButton>(R.id.fabAdd)
        var _rvDaftar = findViewById<RecyclerView>(R.id.rvDaftar)

        _rvDaftar.layoutManager = LinearLayoutManager(this)
        _rvDaftar.adapter = adapterDaftar

        _fabAdd.setOnClickListener {
            startActivity(Intent(this, TambahDaftarActivity::class.java))
        }

        adapterDaftar.setOnItemClickCallback(object : AdapterDaftar.OnItemClickCallback {
            override fun delData(dtBelanja: DaftarBelanja) {
                // Untuk membatalkan update data apabila terjadi kegagalan (kyk db trans commit)
                CoroutineScope(Dispatchers.IO).async {
                    DB.daftarBelanjaDAO().delete(dtBelanja)
                    val daftar = DB.daftarBelanjaDAO().selectAll()
                    withContext(Dispatchers.Main) {
                        adapterDaftar.isiData(daftar)
                    }
                }
            }

            override fun moveData(dtBelanja: DaftarBelanja) {
                CoroutineScope(Dispatchers.IO).async {
//                    val historyBelanja = HistoryBelanja(
//                        tanggal = dtBelanja.tanggal,
//                        item = dtBelanja.item,
//                        jumlah = dtBelanja.jumlah
//                    )
                    CoroutineScope(Dispatchers.IO).async {
                        HistoryDB.historyBelanjaDAO().insert(dtBelanja)
                    }
                    DB.daftarBelanjaDAO().delete(dtBelanja)
                    val daftar = DB.daftarBelanjaDAO().selectAll()
                    withContext(Dispatchers.Main) {
                        adapterDaftar.isiData(daftar)
                    }
                }
            }

        })

        var _fabHistory = findViewById<FloatingActionButton>(R.id.fabHistory)
        _fabHistory.setOnClickListener {
            startActivity(Intent(this, HistoryDaftarActivity::class.java))
        }

    }

    override fun onStart() {
        super.onStart()
        // Untuk membatalkan update data apabila terjadi kegagalan (kyk db trans commit)
        CoroutineScope(Dispatchers.Main).async {
            val daftarBelanja = DB.daftarBelanjaDAO().selectAll()
            adapterDaftar.isiData(daftarBelanja)
            Log.d("data ROOM", daftarBelanja.toString())
        }
    }
}