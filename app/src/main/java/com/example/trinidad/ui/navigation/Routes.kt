package com.example.trinidad.ui.navigation

sealed class Routes(val route: String) {

    object Home : Routes("home")

    object Ligas : Routes("ligas")

    object EquipoDetail : Routes("equipo/{equipoId}") {
        fun createRoute(equipoId: Int) = "equipo/$equipoId"
    }

    object JugadorDetail : Routes("jugador/{jugadorId}") {
        fun createRoute(jugadorId: Int) = "jugador/$jugadorId"
    }

    object EquipoLegendario : Routes("equipo_legendario")
}