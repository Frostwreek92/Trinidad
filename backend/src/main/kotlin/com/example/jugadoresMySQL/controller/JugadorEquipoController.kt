package com.example.jugadoresMySQL.controller

import com.example.jugadoresMySQL.model.Equipo
import com.example.jugadoresMySQL.model.JugadorPorEquipo
import com.example.jugadoresMySQL.service.EquipoPorLigaService
import com.example.jugadoresMySQL.service.EquipoService
import com.example.jugadoresMySQL.service.JugadorPorEquipoService
import com.example.jugadoresMySQL.service.LigaService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*

@Controller
@RequestMapping("/je")
class JugadorEquipoController(
    private val equipoService: EquipoService,
    private val jugadorPorEquipoService: JugadorPorEquipoService,
    private val equipoPorLigaService: EquipoPorLigaService,
    private val ligaService: LigaService
) {

    @GetMapping
    fun index(model: Model): String {
        model.addAttribute("equipos", equipoService.findAll())
        return "jugadoresPorEquipo"
    }

    @GetMapping("/equipos-por-liga")
    fun mostrarEquiposPorLiga(model: Model): String {
        model.addAttribute("ligas", ligaService.findAll())
        return "equiposPorLiga"
    }

    @PostMapping("/equipos-por-liga")
    fun procesarEquiposPorLiga(
        @RequestParam idLiga: Int,
        model: Model
    ): String {
        val relaciones = equipoPorLigaService.findByLiga(idLiga)
        val equipos = relaciones.map { it.equipo }.filterNotNull()
        
        model.addAttribute("equipos", equipos)
        model.addAttribute("ligas", ligaService.findAll())
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
