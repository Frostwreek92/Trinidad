package com.example.jugadoresMySQL.service

import com.example.jugadoresMySQL.model.Jugador
import com.example.jugadoresMySQL.repository.JugadorRepository
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.InputStreamReader

@Service
class JugadorService(
    private val jugadorRepository: JugadorRepository
) {

    fun findAll(): List<Jugador> = jugadorRepository.findAll()

    fun findById(id: Int): Jugador =
        jugadorRepository.findById(id).orElseThrow { NoSuchElementException("Jugador con id $id no encontrado") }

    fun save(jugador: Jugador): Jugador =
        if (jugador.idJugador == null) {
            jugadorRepository.save(jugador)
        } else {
            update(jugador.idJugador!!, jugador)
        }

    fun update(id: Int, updated: Jugador): Jugador {
        val existing = findById(id)

        existing.nombreJugador = updated.nombreJugador
        existing.nombreEquipo = updated.nombreEquipo
        existing.posicion = updated.posicion
        existing.edad = updated.edad
        existing.dorsal = updated.dorsal

        return jugadorRepository.save(existing)
    }

    fun delete(id: Int) {
        if (!jugadorRepository.existsById(id)) {
            throw NoSuchElementException("Jugador con id $id no encontrado")
        }
        jugadorRepository.deleteById(id)
    }

    fun importarDesdeCsv(archivo: MultipartFile): Int {
        val reader = BufferedReader(InputStreamReader(archivo.inputStream))

        val jugadores = reader.lineSequence()
            .drop(1) // saltar cabecera si existe
            .filter { it.isNotBlank() }
            .map { linea ->
                val partes = linea.split(';', ',', '\t')
                    .map { it.trim() }

                if (partes.size < 5) {
                    throw IllegalArgumentException("Línea CSV no válida: '$linea'")
                }

                Jugador(
                    nombreJugador = partes[0],
                    idEquipo = partes[1].toIntOrNull(),
                    posicion = partes[2],
                    edad = partes[3].toInt(),
                    dorsal = partes[4].toInt()
                ).apply {
                    nombreEquipo = partes[1]
                }
            }
            .toList()

        jugadorRepository.saveAll(jugadores)
        return jugadores.size
    }

    fun importarDesdeArchivoFijo(): Int {
        val archivo = File("data/jugadores_ejemplo.csv")
        
        if (!archivo.exists()) {
            throw IllegalArgumentException("El archivo data/jugadores_ejemplo.csv no existe")
        }

        val reader = BufferedReader(FileReader(archivo))

        val jugadores = reader.lineSequence()
            .drop(1) // saltar cabecera
            .filter { it.isNotBlank() }
            .map { linea ->
                val partes = linea.split(',')
                    .map { it.trim() }

                if (partes.size < 5) {
                    throw IllegalArgumentException("Línea CSV no válida: '$linea'")
                }

                Jugador(
                    nombreJugador = partes[0],
                    idEquipo = partes[1].toIntOrNull(),
                    posicion = partes[2],
                    edad = partes[3].toInt(),
                    dorsal = partes[4].toInt()
                ).apply {
                    nombreEquipo = partes[1]
                }
            }
            .toList()

        jugadorRepository.saveAll(jugadores)
        return jugadores.size
    }
}
