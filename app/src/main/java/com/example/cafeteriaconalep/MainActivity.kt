package com.example.cafeteriaconalep

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var rvListaPlatillos: RecyclerView
    lateinit var imprimirBoton: Button
    lateinit var apartadosBoton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvListaPlatillos = findViewById(R.id.rvListaPlatillos)
        imprimirBoton = findViewById(R.id.btnImprimir)
        apartadosBoton = findViewById(R.id.btnApartados)

        val adapter = PlatillosAdapter(PlatillosProvider.listaPlatillos)

        rvListaPlatillos.layoutManager = LinearLayoutManager(this)
        rvListaPlatillos.adapter = adapter

        imprimirBoton.setOnClickListener {
            println(adapter.obtenerSeleccionados())
        }

        apartadosBoton.setOnClickListener {
            val intent = Intent(this, ApartadosActivity::class.java)
            startActivity(intent)
        }
    }
}