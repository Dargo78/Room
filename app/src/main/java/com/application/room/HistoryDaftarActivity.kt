package com.application.room

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.application.room.database.DaftarBelanja
import com.application.room.database.HistoryBelanja
import com.application.room.database.HistoryBelanjaDB
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async

class HistoryDaftarActivity : AppCompatActivity() {
    private lateinit var HistoryDB: HistoryBelanjaDB
    private lateinit var adapterHistory: AdapterHistory
    private var arHistory: MutableList<DaftarBelanja> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_history_daftar)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        HistoryDB = HistoryBelanjaDB.getDatabase(this)
        adapterHistory = AdapterHistory(arHistory)

        var _rvDaftar = findViewById<RecyclerView>(R.id.rvDaftar)
        _rvDaftar.layoutManager = LinearLayoutManager(this)
        _rvDaftar.adapter = adapterHistory
    }

    override fun onStart() {
        super.onStart()
        CoroutineScope(Dispatchers.Main).async {
            Log.i("HEHE", "MSK GAN")
            val historyBelanja = HistoryDB.historyBelanjaDAO().selectAll()
            adapterHistory.isiData(historyBelanja)
            Log.i("APLIKASI", historyBelanja.toString())
        }
    }
}