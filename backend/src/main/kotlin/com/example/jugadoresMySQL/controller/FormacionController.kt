package com.example.jugadoresMySQL.controller

import com.example.jugadoresMySQL.model.Formacion
import com.example.jugadoresMySQL.model.Jugador
import com.example.jugadoresMySQL.model.JugadorEnPosicion
import com.example.jugadoresMySQL.service.FormacionService
import com.example.jugadoresMySQL.service.JugadorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import org.springframework.web.bind.annotation.RequestBody

@RestController("formacionApi")
@RequestMapping("/api/equipo-legendario")
@CrossOrigin(origins = ["*"])
class FormacionController(
    private val formacionService: FormacionService,
    private val jugadorService: JugadorService
) {

    data class FormacionRequest(
        val id: Int? = null,
        val esquema: String = "4-3-3",
        val jugadores: List<JugadorPosicionRequest> = emptyList()
    )

    data class JugadorPosicionRequest(
        val jugador: Jugador,
        val posicion: String,
        val x: Float,
        val y: Float
    )

    data class FormacionResponse(
        val id: Int?,
        val esquema: String,
        val jugadores: List<JugadorPosicionResponse>
    )

    data class JugadorPosicionResponse(
        val jugador: Jugador,
        val posicion: String,
        val x: Float,
        val y: Float
    )

    @GetMapping
    fun getFormacionLegendaria(): ResponseEntity<FormacionResponse> {
        return try {
            val formacion = formacionService.getLatestFormacion()
            if (formacion != null) {
                val response = FormacionResponse(
                    id = formacion.idFormacion,
                    esquema = formacion.esquema,
                    jugadores = formacion.jugadores.map { jugadorEnPosicion ->
                        JugadorPosicionResponse(
                            jugador = jugadorEnPosicion.jugador,
                            posicion = jugadorEnPosicion.posicion,
                            x = jugadorEnPosicion.posicionX,
                            y = jugadorEnPosicion.posicionY
                        )
                    }
                )
                ResponseEntity.ok(response)
            } else {
                ResponseEntity.ok().body(null)
            }
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null)
        }
    }

    @PostMapping
    fun saveFormacionLegendaria(@RequestBody request: FormacionRequest): ResponseEntity<FormacionResponse> {
        return try {
            val targetId = request.id ?: formacionService.getLatestFormacion()?.idFormacion

            // Crear la formación
            val formacion = Formacion(
                idFormacion = targetId,
                esquema = request.esquema
            )

            // Crear los jugadores en posición
            val jugadoresEnPosicion = request.jugadores.map { jugadorReq ->
                // Verificar que el jugador existe
                val jugadorExistente = jugadorService.findById(jugadorReq.jugador.idJugador!!)
                
                JugadorEnPosicion(
                    jugador = jugadorExistente,
                    formacion = formacion,
                    posicion = jugadorReq.posicion,
                    posicionX = jugadorReq.x,
                    posicionY = jugadorReq.y
                )
            }

            formacion.jugadores = jugadoresEnPosicion

            // Guardar la formación
            val savedFormacion = formacionService.save(formacion)

            val response = FormacionResponse(
                id = savedFormacion.idFormacion,
                esquema = savedFormacion.esquema,
                jugadores = savedFormacion.jugadores.map { jugadorEnPosicion ->
                    JugadorPosicionResponse(
                        jugador = jugadorEnPosicion.jugador,
                        posicion = jugadorEnPosicion.posicion,
                        x = jugadorEnPosicion.posicionX,
                        y = jugadorEnPosicion.posicionY
                    )
                }
            )

            ResponseEntity.status(HttpStatus.CREATED).body(response)
        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @DeleteMapping
    fun deleteFormacionLegendaria(): ResponseEntity<Unit> {
        return try {
            val formacion = formacionService.getLatestFormacion()
            if (formacion != null) {
                formacionService.delete(formacion.idFormacion!!)
            }
            ResponseEntity.noContent().build()
        } catch (e: NoSuchElementException) {
            ResponseEntity.noContent().build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    @GetMapping("/exists")
    fun checkExistsFormacionLegendaria(): ResponseEntity<Boolean> {
        return try {
            val exists = formacionService.existsFormacion()
            ResponseEntity.ok(exists)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false)
        }
    }
}
