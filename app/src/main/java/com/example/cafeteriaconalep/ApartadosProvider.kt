package com.example.cafeteriaconalep

object ApartadosProvider {
    private val listaApartados = mutableListOf(
        Apartados("Testing", "Hamburguesa", false),
        Apartados("Testing 2", "Tacos", false),
        Apartados("Testing 3", "Flautas", false)
    )

    val listaApartado: List<Apartados> get() = listaApartados

    fun agregarApartado(apartado: Apartados){
        listaApartados.add(apartado)
    }
}