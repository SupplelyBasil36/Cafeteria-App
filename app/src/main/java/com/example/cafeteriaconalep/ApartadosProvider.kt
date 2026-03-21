package com.example.cafeteriaconalep

class ApartadosProvider {
    companion object {
        val listaApartados = mutableListOf(
            Apartados("Testing", "Hamburguesa", false),
            Apartados("Testing 2", "Tacos", false),
            Apartados("Testing 3", "Flautas", false)
        )
    }
}