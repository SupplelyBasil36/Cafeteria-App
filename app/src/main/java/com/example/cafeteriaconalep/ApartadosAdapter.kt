package com.example.cafeteriaconalep

import android.app.AlertDialog
import android.app.Dialog
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.dialog.MaterialAlertDialogBuilder

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
        p0.apartadoCheck.setOnCheckedChangeListener(null)

        p0.nombreApartado.text = data.nombreApartado
        p0.descPlatillo.text = data.nombrePlatilloApartado
        p0.apartadoCheck.isChecked = data.seleccionado

        p0.apartadoCheck.setOnCheckedChangeListener { _, isChecked ->
            if (isChecked) {
                val build = AlertDialog.Builder(p0.itemView.context)
                build.setTitle("Eliminar apartado")
                    .setMessage("¿Estás seguro de que deseas eliminar \"${data.nombreApartado}\"?")
                    .setNegativeButton("Cancelar") { dialog, _ ->
                        p0.apartadoCheck.setOnCheckedChangeListener(null)
                        p0.apartadoCheck.isChecked = false
                        data.seleccionado = false
                        p0.apartadoCheck.setOnCheckedChangeListener { _, isChecked ->
                            data.seleccionado = isChecked
                        }
                        dialog.dismiss()
                    }
                    .setPositiveButton("Sí, eliminar") { _, _ ->
                        val posicionActual = p0.adapterPosition
                        if (posicionActual != RecyclerView.NO_ID.toInt()) {
                            listaApartados.removeAt(posicionActual)
                            notifyItemRemoved(posicionActual)
                        }
                    }
                    .show()
            } else {
                data.seleccionado = false
            }
        }
    }

    override fun getItemCount(): Int = listaApartados.size

}