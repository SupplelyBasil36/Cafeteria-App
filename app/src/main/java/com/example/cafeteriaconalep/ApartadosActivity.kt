package com.example.cafeteriaconalep

import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ApartadosActivity : AppCompatActivity() {
    lateinit var rvApartados: RecyclerView
    lateinit var btnAgregarApartado: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apartados)
        rvApartados = findViewById(R.id.rvListaApartados)
        btnAgregarApartado = findViewById(R.id.btnAgregarApartado)

        val adapter = ApartadosAdapter(ApartadosProvider.listaApartado as MutableList<Apartados>)
        rvApartados.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        rvApartados.adapter = adapter

        btnAgregarApartado.setOnClickListener {
            val dialog = Dialog(this)
            dialog.setContentView(R.layout.dialog_agregar_apartado)
            dialog.show()

            val btnGuardar = dialog.findViewById<Button>(R.id.btnAgrApartado)
            val apartadoNombre = dialog.findViewById<EditText>(R.id.etAgrNApartado)
            val apartadoPlatillo = dialog.findViewById<EditText>(R.id.etAgrPlatillo)
            val lista = ApartadosProvider

            btnGuardar.setOnClickListener {
                lista.agregarApartado(
                    Apartados(
                        apartadoNombre.text.toString(),
                        apartadoPlatillo.text.toString(),
                        false
                    )
                )
                adapter.notifyItemInserted(lista.listaApartado.size - 1)
                dialog.hide()
            }

        }
    }
}