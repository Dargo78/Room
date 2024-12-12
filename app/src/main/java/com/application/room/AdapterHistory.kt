package com.application.room

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.application.room.database.DaftarBelanja
import com.application.room.database.HistoryBelanja
import com.application.room.database.HistoryBelanjaDB

class AdapterHistory(private val daftarBelanja: MutableList<DaftarBelanja>) :
    RecyclerView.Adapter<AdapterHistory.ListViewHolder>() {

    class ListViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var _tvItemBarang = view.findViewById<TextView>(R.id.tvItemBarang)
        var _tvJumlahBarang = view.findViewById<TextView>(R.id.tvJumlahBarang)
        var _tvTanggal = view.findViewById<TextView>(R.id.tvTanggal)
    }

    fun isiData(history: List<DaftarBelanja>) {
        daftarBelanja.clear()
        daftarBelanja.addAll(history)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterHistory.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(
            R.layout.history_item_list, parent, false
        )
        return ListViewHolder(view)
    }

    override fun getItemCount(): Int {
        return daftarBelanja.size
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        var history = daftarBelanja[position]

        holder._tvTanggal.setText(history.tanggal)
        holder._tvItemBarang.setText(history.item)
        holder._tvJumlahBarang.setText(history.jumlah)
    }

}