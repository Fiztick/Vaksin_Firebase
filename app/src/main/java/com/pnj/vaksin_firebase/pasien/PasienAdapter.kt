package com.pnj.vaksin_firebase.pasien

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.pnj.vaksin_firebase.R

class PasienAdapter(private val pasienList: ArrayList<Pasien>) :
    RecyclerView.Adapter<PasienAdapter.PasienViewHolder>(){

    private lateinit var activity: AppCompatActivity

    class PasienViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val nik: TextView = itemView.findViewById(R.id.TVLNik)
        val nama: TextView = itemView.findViewById(R.id.TVLNama)
        val jenis_kelamin: TextView = itemView.findViewById(R.id.TVLJenisKelamin)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PasienViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.pasien_list_layout, parent, false)
        return PasienViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PasienViewHolder, position: Int) {
        val pasien: Pasien = pasienList[position]
        holder.nik.text = pasien.nik
        holder.nama.text = pasien.nama
        holder.jenis_kelamin.text = pasien.jenis_kelamin

        holder.itemView.setOnClickListener {
            activity = it.context as AppCompatActivity
            activity.startActivity(Intent(activity, EditPasienActivity::class.java).apply {
                putExtra("nik", pasien.nik.toString())
                putExtra("nama", pasien.nama.toString())
                putExtra("tgl_lahir", pasien.tgl_lahir.toString())
                putExtra("jenis_kelamin", pasien.jenis_kelamin.toString())
                putExtra("penyakit_bawaan", pasien.penyakit_bawaan.toString())
            })
        }
    }

    override fun getItemCount(): Int {
        return pasienList.size
    }
}