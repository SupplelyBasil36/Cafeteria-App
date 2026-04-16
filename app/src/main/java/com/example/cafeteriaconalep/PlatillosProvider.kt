package com.example.cafeteriaconalep

object PlatillosProvider {
    var totalDelDia: Double = 0.0
    private val listaPlatillos = mutableListOf(
        Platillos("Hamburguesa", 40, 0),
        Platillos("Hot Dog", 25, 0),
        Platillos("Tacos", 14, 0),
        Platillos("Quesadilla", 30, 0),
        Platillos("Quesadilla maíz", 25, 0),
        Platillos("Burrito", 17, 0),
        Platillos("Torta", 30, 0),
        Platillos("Torta con queso", 35, 0),
        Platillos("Gorditas", 20, 0),
        Platillos("Sandwich", 25, 0),
        Platillos("Molletes", 20, 0),
        Platillos("Ensaladas", 35, 0),
        Platillos("Platillo", 50, 0),
        Platillos("Chilaquiles sencillos", 45, 0),
        Platillos("Chilaquiles con proteina", 50, 0)
    )

    val listaPlatillo: List<Platillos> get() = listaPlatillos
}