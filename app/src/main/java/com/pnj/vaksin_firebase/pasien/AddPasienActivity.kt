package com.pnj.vaksin_firebase.pasien

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.firebase.firestore.FirebaseFirestore
import com.pnj.vaksin_firebase.MainActivity
import com.pnj.vaksin_firebase.R
import com.pnj.vaksin_firebase.databinding.ActivityAddPasienBinding
import java.util.Calendar

class AddPasienActivity : AppCompatActivity() {

    private lateinit var binding: ActivityAddPasienBinding
    private val firestoreDatabase = FirebaseFirestore.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddPasienBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.TxtAddTglLahir.setOnClickListener {
            val c = Calendar.getInstance()
            val year = c.get(Calendar.YEAR)
            val month = c.get(Calendar.MONTH)
            val day = c.get(Calendar.DAY_OF_MONTH)

            val dpd = DatePickerDialog(this,
                DatePickerDialog.OnDateSetListener{ view, year, monthOfYear, dayOfMonth ->
                    binding.TxtAddTglLahir.setText("" + year + "-" + monthOfYear + "-" + dayOfMonth)
                }, year, month, day)

            dpd.show()
        }

        binding.BtnAddPasien.setOnClickListener {
            val intentMain = Intent(this, AddPasienActivity::class.java)
            startActivity(intentMain)
        }
    }

    fun addPasien() {
        var nik : String = binding. TxtAddNIK.text.toString()
        var nama : String = binding.TxtAddNama.text.toString()
        var tgl_lahir : String = binding.TxtAddTglLahir.toString()

        var jk : String = ""
        if(binding.RdnEditJKL.isChecked) {
            jk = "Laki - laki"
        }
        else if(binding.RdnEditJKP.isChecked) {
            jk = "Perempuan"
        }

        var penyakit = ArrayList<String>()
        if(binding.ChkDiabetes.isChecked) {
            penyakit.add("diabetes")
        }
        if(binding.ChkJantung.isChecked) {
            penyakit.add("jantung")
        }
        if(binding.ChkAsma.isChecked) {
            penyakit.add("asma")
        }

        val penyakit_string = penyakit.joinToString("|")

        val pasien: MutableMap<String, Any> = HashMap()
        pasien["nik"] = nik
        pasien["nama"] = nama
        pasien["tgl_lahir"] = tgl_lahir
        pasien["jenis_kelamin"] = jk
        pasien["penyakit_bawaan"] = penyakit_string

        firestoreDatabase.collection("pasien").add(pasien)
            .addOnSuccessListener {
                val intentMain = Intent(this, MainActivity::class.java)
                startActivity(intentMain)
            }

        binding.BtnAddPasien.setOnClickListener {
            addPasien()
        }
    }
}