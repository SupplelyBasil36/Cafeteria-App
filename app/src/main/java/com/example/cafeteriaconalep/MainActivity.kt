package com.example.cafeteriaconalep

import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    lateinit var rvListaPlatillos: RecyclerView
    lateinit var imprimirBoton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        rvListaPlatillos = findViewById(R.id.rvListaPlatillos)
        imprimirBoton = findViewById(R.id.btnImprimir)
        val adapter = PlatillosAdapter(PlatillosProvider.listaPlatillos)
        rvListaPlatillos.layoutManager = LinearLayoutManager(this)
        rvListaPlatillos.adapter = adapter

        imprimirBoton.setOnClickListener {
            println(adapter.obtenerSeleccionados())
        }
    }
}