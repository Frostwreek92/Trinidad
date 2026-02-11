package com.example.jugadoresMySQL.controller

import com.example.jugadoresMySQL.model.Liga
import com.example.jugadoresMySQL.service.LigaService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.*
import org.springframework.web.servlet.mvc.support.RedirectAttributes

@Controller
@RequestMapping("/ligas")
class LigaController(
    private val ligaService: LigaService
) {

    @GetMapping
    fun listar(model: Model): String {
        model.addAttribute("ligas", ligaService.findAll())
        return "ligas"
    }

    @GetMapping("/{id}")
    fun detalle(@PathVariable id: Int, model: Model): String =
        try {
            val liga = ligaService.findById(id)
            model.addAttribute("liga", liga)
            "detalleLiga"
        } catch (e: NoSuchElementException) {
            "errorLiga"
        }

    @GetMapping("/nuevo")
    fun nuevo(model: Model): String {
        model.addAttribute("liga", Liga())
        model.addAttribute("titulo", "Nueva liga")
        return "formularioLiga"
    }

    @GetMapping("/editar/{id}")
    fun editar(@PathVariable id: Int, model: Model): String =
        try {
            val liga = ligaService.findById(id)
            model.addAttribute("liga", liga)
            model.addAttribute("titulo", "Editar liga")
            "formularioLiga"
        } catch (e: NoSuchElementException) {
            "errorLiga"
        }

    @PostMapping("/guardar")
    fun guardar(@ModelAttribute liga: Liga): String {
        ligaService.save(liga)
        return "redirect:/ligas"
    }

    @GetMapping("/borrar/{id}")
    fun borrar(@PathVariable id: Int): String {
        ligaService.delete(id)
        return "redirect:/ligas"
    }

    @GetMapping("/importar")
    fun mostrarImportar(): String = "ligasImportar"

    @PostMapping("/importar")
    fun importar(redirectAttributes: RedirectAttributes): String {
        return try {
            val count = ligaService.importarDesdeArchivoFijo()
            redirectAttributes.addFlashAttribute("mensajeExito", "$count ligas importadas correctamente")
            "redirect:/ligas"
        } catch (ex: Exception) {
            redirectAttributes.addFlashAttribute("mensajeError", "Error al importar el CSV: ${ex.message}")
            "redirect:/ligas/importar"
        }
    }
}
