package com.example.cafeteriaconalep

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class ApartadosActivity : AppCompatActivity() {
    lateinit var rvApartados: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_apartados)

        rvApartados = findViewById(R.id.rvListaApartados)
        val adapter = ApartadosAdapter(ApartadosProvider.listaApartados)
        rvApartados.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        rvApartados.adapter = adapter
    }
}