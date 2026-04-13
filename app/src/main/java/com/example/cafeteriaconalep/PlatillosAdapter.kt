package com.example.cafeteriaconalep

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

class PlatillosAdapter(private val listaPlatillos: List<Platillos>) :
    RecyclerView.Adapter<PlatillosViewHolder>() {
    override fun onCreateViewHolder(
        p0: ViewGroup,
        p1: Int
    ): PlatillosViewHolder {
        val view = LayoutInflater.from(p0.context).inflate(R.layout.item_platillos_list, p0, false)
        return PlatillosViewHolder(view)
    }

    override fun onBindViewHolder(
        p0: PlatillosViewHolder,
        p1: Int
    ) {
        p0.render(listaPlatillos[p1])
    }

    override fun getItemCount(): Int {
        return listaPlatillos.size
    }

    fun obtenerSeleccionados(): List<Platillos> {
        //Retornamos una lista de nombres de platillos donde la cantidad sea mayor a 0, si son ceros no retornar valores
        //val seleccionados = listaPlatillos.filter { it.cantidad > 0 }.map { it.nombrePlatillo }
        val seleccionados = listaPlatillos.filter { it.cantidad > 0 }
        return seleccionados
    }

    fun limpiarSeleccionados() {
        listaPlatillos.forEach { it.cantidad = 0 }
        notifyDataSetChanged()
    }

    //Esta funcion se utiliza para tomar el total de los platillos seleccionados
    fun obtenerTotal(): Int {
        val seleccionados =
            listaPlatillos.filter { it.cantidad > 0 }.sumOf { it.precioPlatillo * it.cantidad }
        return seleccionados
    }
}