package com.example.cafeteriaconalep

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AlertDialog
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

        val adapter = PlatillosAdapter(PlatillosProvider.listaPlatillo)

        rvListaPlatillos.layoutManager = LinearLayoutManager(this)
        rvListaPlatillos.adapter = adapter

        imprimirBoton.setOnClickListener {
            val datos = adapter.obtenerSeleccionados()
            println(datos)
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Imprimir")
            builder.setMessage("La lista se esta imprimiendo")
            builder.setPositiveButton("Aceptar") { dialog, which ->
                // Acción al presionar Aceptar (puede estar vacía)
                println(adapter.obtenerTotal()) //Obtenemos el costo total del ticket
                adapter.limpiarSeleccionados()
                adapter.notifyDataSetChanged()
                dialog.cancel()
            }
            builder.setNegativeButton("Cancelar") { dialog, which ->
                // Acción al presionar Cancelar
            }
            builder.show()
            //Esto funciona para agregar un platillo a la lista
            //PlatillosProvider.listaPlatillos.add(Platillos("Agregado extra", 55, false))
            //adapter.notifyDataSetChanged()
        }

        apartadosBoton.setOnClickListener {
            val intent = Intent(this, ApartadosActivity::class.java)
            startActivity(intent)
        }

    }
}