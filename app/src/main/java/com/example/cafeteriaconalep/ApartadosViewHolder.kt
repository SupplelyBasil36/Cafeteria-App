package com.example.cafeteriaconalep

import android.view.View
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class ApartadosViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val nombreApartado: TextView = view.findViewById(R.id.txtNombreApartado)
    val descPlatillo: TextView = view.findViewById(R.id.txtPlatillo)
    val apartadoCheck: CheckBox = view.findViewById(R.id.chkApartadoSelect)
}