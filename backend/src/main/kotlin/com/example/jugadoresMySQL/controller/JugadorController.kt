package com.example.jugadoresMySQL.controller

import com.example.jugadoresMySQL.model.Jugador
import com.example.jugadoresMySQL.service.JugadorService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.multipart.MultipartFile
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/jugadores")
class JugadorController(
    private val jugadorService: JugadorService
) {

    @GetMapping
    fun listar(model: Model): String {
        model.addAttribute("jugadores", jugadorService.findAll())
        return "jugadores"
    }

    @GetMapping("/{id}")
    fun detalle(@PathVariable id: Int, model: Model): String =
        try {
            val jugador = jugadorService.findById(id)
            model.addAttribute("jugador", jugador)
            "detalleJugador"
        } catch (e: NoSuchElementException) {
            "errorJugador"
        }

    @GetMapping("/nuevo")
    fun nuevo(model: Model): String {
        model.addAttribute("jugador", Jugador())
        model.addAttribute("titulo", "Nuevo jugador")
        return "formularioJugador"
    }

    @GetMapping("/editar/{id}")
    fun editar(@PathVariable id: Int, model: Model): String =
        try {
            val jugador = jugadorService.findById(id)
            model.addAttribute("jugador", jugador)
            model.addAttribute("titulo", "Editar jugador")
            "formularioJugador"
        } catch (e: NoSuchElementException) {
            "errorJugador"
        }

    @PostMapping("/guardar")
    fun guardar(@ModelAttribute jugador: Jugador): String {
        jugadorService.save(jugador)
        return "redirect:/jugadores"
    }

    @GetMapping("/borrar/{id}")
    fun borrar(@PathVariable id: Int): String {
        jugadorService.delete(id)
        return "redirect:/jugadores"
    }

    @GetMapping("/importar")
    fun mostrarImportar(): String = "jugadoresImportar"

    @PostMapping("/importar")
    fun importar(
        @RequestParam("file") file: MultipartFile,
        redirectAttributes: RedirectAttributes
    ): String {
        if (file.isEmpty) {
            redirectAttributes.addFlashAttribute("mensajeError", "Selecciona un archivo CSV")
            return "redirect:/jugadores/importar"
        }

        return try {
            val count = jugadorService.importarDesdeCsv(file)
            redirectAttributes.addFlashAttribute("mensajeExito", "$count jugadores importados correctamente")
            "redirect:/jugadores"
        } catch (ex: Exception) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al importar el CSV: ${ex.message}")
            "redirect:/jugadores/importar"
        }
    }
}
