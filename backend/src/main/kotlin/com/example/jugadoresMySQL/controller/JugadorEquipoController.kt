package com.example.jugadoresMySQL.controller

import com.example.jugadoresMySQL.model.Equipo
import com.example.jugadoresMySQL.model.JugadorPorEquipo
import com.example.jugadoresMySQL.service.EquipoService
import com.example.jugadoresMySQL.service.JugadorPorEquipoService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/je")
class JugadorEquipoController(
    private val equipoService: EquipoService,
    private val jugadorPorEquipoService: JugadorPorEquipoService
) {

    @GetMapping
    fun index(model: Model): String {
        model.addAttribute("equipos", equipoService.findAll())
        return "jugadoresPorEquipo"
    }

    @GetMapping("/equipos-por-liga")
    fun mostrarEquiposPorLiga(): String = "equiposPorLiga"

    @PostMapping("/equipos-por-liga")
    fun procesarEquiposPorLiga(
        @RequestParam idLiga: Int,
        model: Model
    ): String {
        // Por ahora mostramos todos los equipos
        model.addAttribute("equipos", equipoService.findAll())
        model.addAttribute("idLigaSeleccionada", idLiga)
        return "equiposPorLiga"
    }

    @GetMapping("/jugadores-por-equipo")
    fun mostrarJugadoresPorEquipo(model: Model): String {
        model.addAttribute("equipos", equipoService.findAll())
        return "jugadoresPorEquipo"
    }

    @PostMapping("/jugadores-por-equipo")
    fun procesarJugadoresPorEquipo(
        @RequestParam idEquipo: Int,
        model: Model
    ): String {
        val relaciones = jugadorPorEquipoService.findByEquipo(idEquipo)
        model.addAttribute("relaciones", relaciones)
        model.addAttribute("equipos", equipoService.findAll())
        model.addAttribute("idEquipoSeleccionado", idEquipo)
        return "jugadoresPorEquipo"
    }
}
