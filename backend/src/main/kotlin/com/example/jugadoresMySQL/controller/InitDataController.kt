package com.example.jugadoresMySQL.controller

import com.example.jugadoresMySQL.config.DataInitializer
import com.example.jugadoresMySQL.service.EquipoService
import com.example.jugadoresMySQL.service.JugadorService
import com.example.jugadoresMySQL.service.LigaService
import com.example.jugadoresMySQL.service.JugadorPorEquipoService
import com.example.jugadoresMySQL.service.EquipoPorLigaService
import org.springframework.stereotype.Controller
import org.springframework.ui.Model
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.ResponseBody

@Controller
class InitDataController(
    private val dataInitializer: DataInitializer,
    private val equipoService: EquipoService,
    private val jugadorService: JugadorService,
    private val ligaService: LigaService,
    private val jugadorPorEquipoService: JugadorPorEquipoService,
    private val equipoPorLigaService: EquipoPorLigaService
) {

    @GetMapping("/init-data")
    fun initDataPage(model: Model): String {
        val status = mapOf(
            "equipos" to equipoService.findAll().size,
            "jugadores" to jugadorService.findAll().size,
            "ligas" to ligaService.findAll().size,
            "relacionesJE" to jugadorPorEquipoService.findAll().size,
            "relacionesEL" to equipoPorLigaService.findAll().size
        )
        model.addAttribute("status", status)
        return "initData"
    }

    @PostMapping("/init-data/importar")
    @ResponseBody
    fun importarDatos(): Map<String, Any> {
        try {
            val resultados = mutableMapOf<String, Int>()
            
            // Importar si las tablas están vacías
            if (equipoService.findAll().isEmpty()) {
                resultados["equipos"] = equipoService.importarDesdeArchivoFijo()
            }
            
            if (ligaService.findAll().isEmpty()) {
                resultados["ligas"] = ligaService.importarDesdeArchivoFijo()
            }
            
            if (jugadorService.findAll().isEmpty()) {
                resultados["jugadores"] = jugadorService.importarDesdeArchivoFijo()
            }
            
            // Crear relaciones si no existen
            if (jugadorPorEquipoService.findAll().isEmpty() && 
                equipoService.findAll().isNotEmpty() && 
                jugadorService.findAll().isNotEmpty()) {
                
                dataInitializer.crearRelacionesJugadoresEquipos()
                resultados["relaciones_jugadores_equipos"] = jugadorPorEquipoService.findAll().size
            }
            
            if (equipoPorLigaService.findAll().isEmpty() && 
                equipoService.findAll().isNotEmpty() && 
                ligaService.findAll().isNotEmpty()) {
                
                dataInitializer.crearRelacionesEquiposLigas()
                resultados["relaciones_equipos_ligas"] = equipoPorLigaService.findAll().size
            }
            
            return mapOf(
                "success" to true,
                "message" to "Datos importados correctamente",
                "resultados" to resultados
            )
        } catch (e: Exception) {
            return mapOf(
                "success" to false,
                "message" to "Error: ${e.message}"
            )
        }
    }
}
