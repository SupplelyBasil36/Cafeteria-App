package com.example.cafeteriaconalep

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class ApartadosAdapter(private val listaApartados: List<Apartados>): RecyclerView.Adapter<ApartadosViewHolder>(){
    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): ApartadosViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.activity_apartados, p0, false)
        return ApartadosViewHolder(view)
    }

    override fun onBindViewHolder(
        p0: ApartadosViewHolder,
        p1: Int
    ) {

    }

    override fun getItemCount(): Int = listaApartados.size

}