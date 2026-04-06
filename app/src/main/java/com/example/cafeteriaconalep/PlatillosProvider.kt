package com.example.cafeteriaconalep

object PlatillosProvider {
    private val listaPlatillos = mutableListOf(
        Platillos("Hamburguesa", 55, 0),
        Platillos("Hot Dog", 30, 0),
        Platillos("Tacos", 45, 0),
        Platillos("Quesadilla", 25, 0),
        Platillos("Burrito", 50, 0),
        Platillos("Torta", 40, 0),
        Platillos("Sincronizada", 35, 0),
        Platillos("Enchiladas", 60, 0),
        Platillos("Chilaquiles", 50, 0),
        Platillos("Molletes", 30, 0)
    )

    val listaPlatillo: List<Platillos> get() = listaPlatillos
}