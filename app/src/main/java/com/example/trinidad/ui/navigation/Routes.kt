package com.example.trinidad.ui.navigation

sealed class Routes(val route: String) {
    object Home : Routes("home")
    object Ligas : Routes("ligas")

    
    object EquipoDetail : Routes("equipo_detail/{equipoId}") {
        fun createRoute(equipoId: Int): String = "equipo_detail/$equipoId"
    }
    
    object JugadorDetail : Routes("jugador_detail/{jugadorId}") {
        fun createRoute(jugadorId: Int): String = "jugador_detail/$jugadorId"
    }
    object EquipoLegendario : Routes("equipo_legendario")
}
