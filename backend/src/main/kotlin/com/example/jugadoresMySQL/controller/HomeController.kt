package com.example.jugadoresMySQL.controller

import com.example.jugadoresMySQL.service.JugadorService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping

@Controller
class HomeController(
    private val jugadorService: JugadorService
) {

    @GetMapping("/")
    fun home(model: Model): String {
        val jugadores = jugadorService.findAll()
        model.addAttribute("totalJugadores", jugadores.size)
        model.addAttribute("totalEquipos", jugadores.mapNotNull { it.nombreEquipo }.distinct().size)
        model.addAttribute("jugadoresRecientes", jugadores.takeLast(5))
        
        // Statistics
        model.addAttribute("jugadoresJovenes", jugadores.count { it.edad?.let { edad -> edad in 18..25 } == true })
        model.addAttribute("jugadoresVeteranos", jugadores.count { it.edad?.let { edad -> edad in 26..30 } == true })
        model.addAttribute("jugadoresExpertos", jugadores.count { it.edad?.let { edad -> edad > 30 } == true })
        
        return "index"
    }
}
