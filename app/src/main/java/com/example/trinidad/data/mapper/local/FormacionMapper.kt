package com.example.trinidad.data.mapper.local

import com.example.trinidad.data.remote.local.FormacionDto
import com.example.trinidad.data.remote.local.JugadorEnPosicionDto
import com.example.trinidad.domain.model.api.Player
import com.example.trinidad.domain.model.local.Formacion
import com.example.trinidad.domain.model.local.JugadorEnPosicion
import com.example.trinidad.data.remote.local.BackendJugadorDto

object FormacionMapper {
    
    fun mapToDomain(dto: FormacionDto?): Formacion? {
        return dto?.let {
            Formacion(
                id = it.id,
                esquema = it.esquema,
                jugadores = it.jugadores.map { jugadorDto ->
                    JugadorEnPosicion(
                        jugador = Player(
                            id = jugadorDto.jugador.idJugador ?: 0,
                            name = jugadorDto.jugador.nombreJugador,
                            position = jugadorDto.jugador.posicion,
                            photo = jugadorDto.jugador.foto,
                            height = "altura",
                            weight = "peso",
                            nationality = "nacionalidad"
                        ),
                        posicion = jugadorDto.posicion,
                        x = jugadorDto.x,
                        y = jugadorDto.y
                    )
                }
            )
        }
    }
    
    fun mapToDto(formacion: Formacion): FormacionDto {
        return FormacionDto(
            id = formacion.id,
            esquema = formacion.esquema,
            jugadores = formacion.jugadores.map { jugador ->
                JugadorEnPosicionDto(
                    jugador = BackendJugadorDto(
                        idJugador = jugador.jugador.id,
                        nombreJugador = jugador.jugador.name,
                        idEquipo = null, // No lo necesitamos para la formación
                        posicion = jugador.jugador.position,
                        edad = 0, // Valor por defecto ya que no existe en Player
                        dorsal = 0, // Valor por defecto ya que no existe en Player
                        foto = jugador.jugador.photo,
                        nombreEquipo = null // No existe en Player
                    ),
                    posicion = jugador.posicion,
                    x = jugador.x,
                    y = jugador.y
                )
            }
        )
    }
}
