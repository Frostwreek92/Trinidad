package com.example.jugadoresMySQL.controller

import com.example.jugadoresMySQL.model.Equipo
import com.example.jugadoresMySQL.service.EquipoService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/equipos")
class EquipoController(
    private val equipoService: EquipoService
) {

    @GetMapping
    fun listar(model: Model): String {
        model.addAttribute("equipos", equipoService.findAll())
        return "equipos"
    }

    @GetMapping("/{id}")
    fun detalle(@PathVariable id: Int, model: Model): String =
        try {
            val equipo = equipoService.findById(id)
            model.addAttribute("equipo", equipo)
            "detalleEquipo"
        } catch (e: NoSuchElementException) {
            "errorEquipo"
        }

    @GetMapping("/nuevo")
    fun nuevo(model: Model): String {
        model.addAttribute("equipo", Equipo())
        model.addAttribute("titulo", "Nuevo equipo")
        return "formularioEquipo"
    }

    @GetMapping("/editar/{id}")
    fun editar(@PathVariable id: Int, model: Model): String =
        try {
            val equipo = equipoService.findById(id)
            model.addAttribute("equipo", equipo)
            model.addAttribute("titulo", "Editar equipo")
            "formularioEquipo"
        } catch (e: NoSuchElementException) {
            "errorEquipo"
        }

    @PostMapping("/guardar")
    fun guardar(@ModelAttribute equipo: Equipo): String {
        equipoService.save(equipo)
        return "redirect:/equipos"
    }

    @GetMapping("/borrar/{id}")
    fun borrar(@PathVariable id: Int): String {
        equipoService.delete(id)
        return "redirect:/equipos"
    }

    @GetMapping("/importar")
    fun mostrarImportar(): String = "equiposImportar"

    @PostMapping("/importar")
    fun importar(redirectAttributes: RedirectAttributes): String {
        return try {
            val count = equipoService.importarDesdeArchivoFijo()
            redirectAttributes.addFlashAttribute("mensajeExito", "$count equipos importados correctamente")
            "redirect:/equipos"
        } catch (ex: Exception) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al importar el CSV: ${ex.message}")
            "redirect:/equipos/importar"
        }
    }
}
