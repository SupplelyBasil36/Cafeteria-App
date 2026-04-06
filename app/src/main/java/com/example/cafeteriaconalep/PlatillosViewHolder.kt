package com.example.cafeteriaconalep

import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlatillosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val nombrePlatillo = view.findViewById<TextView>(R.id.txtPlatillo)
    val precioPlatillo = view.findViewById<TextView>(R.id.txtPrecio)
    var cantidadPlatillo = view.findViewById<TextView>(R.id.txtCantidad)

    val btnMas = view.findViewById<Button>(R.id.btnMas)
    val btnMen = view.findViewById<Button>(R.id.btnMenos)

    //var seleccionadoPlatillo = view.findViewById<CheckBox>(R.id.chkSeleccionado)

    fun render(item: Platillos) {
        nombrePlatillo.text = item.nombrePlatillo
        precioPlatillo.text = "Precio: $${item.precioPlatillo}"
        cantidadPlatillo.text = item.cantidad.toString()

        // Quitar listeners anteriores antes de asignar nuevos
        btnMas.setOnClickListener(null)
        btnMen.setOnClickListener(null)

        btnMas.setOnClickListener {
            item.cantidad++
            cantidadPlatillo.text = item.cantidad.toString()
        }

        btnMen.setOnClickListener {
            if (item.cantidad > 0) {
                item.cantidad--
                cantidadPlatillo.text = item.cantidad.toString()
            }
        }
    }

    //seleccionadoPlatillo.isChecked = item.seleccionado
    /*seleccionadoPlatillo.setOnCheckedChangeListener { _, isChecked ->
        item.seleccionado = isChecked //item.seleccionado es igual a true
    }*/
}