package com.example.cafeteriaconalep

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class PlatillosViewHolder(view: View): RecyclerView.ViewHolder(view) {
    val nombrePlatillo = view.findViewById<TextView>(R.id.txtPlatillo)
    val precioPlatillo = view.findViewById<TextView>(R.id.txtPrecio)
    var seleccionadoPlatillo = view.findViewById<CheckBox>(R.id.chkSeleccionado)

    fun render(item: Platillos){
        nombrePlatillo.text = item.nombrePlatillo
        precioPlatillo.text = item.precioPlatillo.toString()
        seleccionadoPlatillo.isChecked = item.seleccionado

        seleccionadoPlatillo.setOnCheckedChangeListener { _, isChecked ->
            item.seleccionado = isChecked
        }
    }
}