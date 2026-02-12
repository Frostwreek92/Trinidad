package com.example.jugadoresMySQL.controller

import com.example.jugadoresMySQL.model.Formacion
import com.example.jugadoresMySQL.model.Jugador
import com.example.jugadoresMySQL.model.JugadorEnPosicion
import com.example.jugadoresMySQL.service.FormacionService
import com.example.jugadoresMySQL.service.JugadorService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

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

    // =========================
    // GET
    // =========================

    @GetMapping
    fun getFormacionLegendaria(): ResponseEntity<Any> {
        return try {
            val formacion = formacionService.getLatestFormacion()

            if (formacion != null) {
                val response = FormacionResponse(
                    id = formacion.idFormacion,
                    esquema = formacion.esquema,
                    jugadores = formacion.jugadores.map {
                        JugadorPosicionResponse(
                            jugador = it.jugador,
                            posicion = it.posicion,
                            x = it.posicionX,
                            y = it.posicionY
                        )
                    }
                )
                ResponseEntity.ok(response)
            } else {
                ResponseEntity.ok().build()
            }

        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    // =========================
    // POST (crear / actualizar)
    // =========================

    @PostMapping
    fun saveFormacionLegendaria(
        @RequestBody request: FormacionRequest
    ): ResponseEntity<FormacionResponse> {

        return try {

            val formacion: Formacion = if (request.id != null) {
                formacionService.findById(request.id)
            } else {
                Formacion(esquema = request.esquema)
            }

            formacion.esquema = request.esquema

            val jugadoresEnPosicion = request.jugadores.map { jugadorReq ->

                val jugadorExistente =
                    jugadorService.findById(jugadorReq.jugador.idJugador!!)

                JugadorEnPosicion(
                    jugador = jugadorExistente,
                    formacion = formacion,
                    posicion = jugadorReq.posicion,
                    posicionX = jugadorReq.x,
                    posicionY = jugadorReq.y
                )
            }

            // 🔥 Reemplazar lista completa
            formacion.jugadores = jugadoresEnPosicion.toMutableList()

            val savedFormacion = formacionService.save(formacion)

            val response = FormacionResponse(
                id = savedFormacion.idFormacion,
                esquema = savedFormacion.esquema,
                jugadores = savedFormacion.jugadores.map {
                    JugadorPosicionResponse(
                        jugador = it.jugador,
                        posicion = it.posicion,
                        x = it.posicionX,
                        y = it.posicionY
                    )
                }
            )

            ResponseEntity.ok(response)

        } catch (e: NoSuchElementException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    // =========================
    // DELETE
    // =========================

    @DeleteMapping
    fun deleteFormacionLegendaria(): ResponseEntity<Unit> {
        return try {
            val formacion = formacionService.getLatestFormacion()
            if (formacion != null) {
                formacionService.delete(formacion.idFormacion!!)
            }
            ResponseEntity.noContent().build()
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build()
        }
    }

    // =========================
    // EXISTS
    // =========================

    @GetMapping("/exists")
    fun checkExistsFormacionLegendaria(): ResponseEntity<Boolean> {
        return try {
            ResponseEntity.ok(formacionService.existsFormacion())
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false)
        }
    }
}
