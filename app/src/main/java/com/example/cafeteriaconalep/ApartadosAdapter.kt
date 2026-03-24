package com.example.cafeteriaconalep

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.RecyclerView

class ApartadosAdapter(private val listaApartados: MutableList<Apartados>) :
    RecyclerView.Adapter<ApartadosViewHolder>() {
    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): ApartadosViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_apartados_list, p0, false)
        return ApartadosViewHolder(view)
    }

    override fun onBindViewHolder(
        p0: ApartadosViewHolder,
        p1: Int
    ) {
        val data = listaApartados[p1]

        p0.nombreApartado.text = data.nombreApartado
        p0.descPlatillo.text = data.nombrePlatilloApartado
        p0.apartadoCheck.isChecked = data.seleccionado

        p0.apartadoCheck.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val context = p0.itemView.context
                val build = AlertDialog.Builder(context)

            }
        }
    }

    override fun getItemCount(): Int = listaApartados.size

}