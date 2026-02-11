package com.example.trinidad.equipoLegendario.data.mapper

import com.example.trinidad.equipoLegendario.data.remote.dto.FormacionDto
import com.example.trinidad.equipoLegendario.data.remote.dto.JugadorEnPosicionDto
import com.example.trinidad.equipoLegendario.domain.model.Formacion
import com.example.trinidad.equipoLegendario.domain.model.JugadorEnPosicion
import com.example.trinidad.domain.model.Player
import com.example.trinidad.equipoLegendario.data.remote.dto.BackendJugadorDto

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
                            photo = jugadorDto.jugador.foto
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
