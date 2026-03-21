package com.example.cafeteriaconalep

class PlatillosProvider {
    companion object {
        val listaPlatillos = mutableListOf(
            Platillos("Hamburguesa", 55, false),
            Platillos("Hot Dog", 30, false),
            Platillos("Tacos", 45, false),
            Platillos("Quesadilla", 25, false),
            Platillos("Burrito", 50, false),
            Platillos("Torta", 40, false),
            Platillos("Sincronizada", 35, false),
            Platillos("Enchiladas", 60, false),
            Platillos("Chilaquiles", 50, false),
            Platillos("Molletes", 30, false)
        )
    }
}