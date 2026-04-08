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

        //VISTAS------------------------------------
        rvListaPlatillos = findViewById(R.id.rvListaPlatillos)
        imprimirBoton = findViewById(R.id.btnImprimir)
        apartadosBoton = findViewById(R.id.btnApartados)
        //------------------------------------------

        val listaPLatillos = PlatillosProvider.listaPlatillo
        val adapter = PlatillosAdapter(listaPLatillos)
        rvListaPlatillos.layoutManager = LinearLayoutManager(this)
        rvListaPlatillos.adapter = adapter

        imprimirBoton.setOnClickListener {
            val datos = adapter.obtenerSeleccionados()
            val total = adapter.obtenerTotal() //Obtenemos el costo total del ticket

            println(datos)
            // Convertir los platillos a líneas de texto para el ticket
            val lineas = datos.map { platillo ->
                "${platillo.nombrePlatillo}  x${platillo.cantidad}  $${platillo.precioPlatillo}"
            }

            // Imprimir en hilo secundario para no bloquear la UI
            Thread {
                val exito = ImpresoraBluetooth.imprimir(
                    context = this,
                    nombreImpresora = "BlueDreamer", // parte del nombre del dispositivo BT
                    lineas = lineas,
                    total = total
                )

                runOnUiThread {
                    val builder = AlertDialog.Builder(this)
                    builder.setTitle("Imprimir")
                    builder.setMessage(
                        if (exito) "La lista se está imprimiendo ✅"
                        else "❌ No se pudo conectar a la impresora. ¿Está encendida y emparejada?"
                    )
                    builder.setPositiveButton("Aceptar") { dialog, _ ->
                        if (exito) {
                            adapter.limpiarSeleccionados()
                            adapter.notifyDataSetChanged()
                        }
                        dialog.cancel()
                    }
                    builder.show()
                }
            }.start()


            /*val builder = AlertDialog.Builder(this)
            builder.setTitle("Imprimir")
            builder.setMessage("La lista se esta imprimiendo")
            builder.setPositiveButton("Aceptar") { dialog, which ->
                // Acción al presionar Aceptar (puede estar vacía)
                println(adapter.obtenerTotal()) //Obtenemos el costo total del ticket
                adapter.limpiarSeleccionados()
                dialog.cancel()
            }
            builder.setNegativeButton("Cancelar") { dialog, which ->
                // Acción al presionar Cancelar
            }
            builder.show()*/
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