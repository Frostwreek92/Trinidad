package com.example.jugadoresMySQL.service

import com.example.jugadoresMySQL.model.Jugador
import com.example.jugadoresMySQL.repository.JugadorRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.*
import org.mockito.MockitoAnnotations
import java.util.*

class JugadorServiceBasicTest {

    @Mock
    private lateinit var jugadorRepository: JugadorRepository

    @InjectMocks
    private lateinit var jugadorService: JugadorService

    private lateinit var jugadorTest: Jugador

    @BeforeEach
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        
        jugadorTest = Jugador(
            idJugador = 1,
            nombreJugador = "Lionel Messi",
            nombreEquipo = "PSG",
            posicion = "Delantero",
            edad = 35,
            dorsal = 10
        )
    }

    @Test
    fun `findAll should return all players`() {
        // Given
        val jugadoresList = listOf(jugadorTest)
        `when`(jugadorRepository.findAll()).thenReturn(jugadoresList)

        // When
        val result = jugadorService.findAll()

        // Then
        assert(result.size == 1)
        assert(result[0].nombreJugador == "Lionel Messi")
        verify(jugadorRepository).findAll()
    }

    @Test
    fun `findById should return player when exists`() {
        // Given
        `when`(jugadorRepository.findById(1)).thenReturn(Optional.of(jugadorTest))

        // When
        val result = jugadorService.findById(1)

        // Then
        assert(result.idJugador == 1)
        assert(result.nombreJugador == "Lionel Messi")
        verify(jugadorRepository).findById(1)
    }

    @Test
    fun `findById should throw exception when player not found`() {
        // Given
        `when`(jugadorRepository.findById(999)).thenReturn(Optional.empty())

        // When & Then
        assertThrows<NoSuchElementException> {
            jugadorService.findById(999)
        }
        verify(jugadorRepository).findById(999)
    }

    @Test
    fun `save should create new player when id is null`() {
        // Given
        val newJugador = Jugador(
            nombreJugador = "Neymar Jr",
            nombreEquipo = "Al Hilal",
            posicion = "Delantero",
            edad = 31,
            dorsal = 10
        )
        
        val savedJugador = Jugador(
            idJugador = 3,
            nombreJugador = "Neymar Jr",
            nombreEquipo = "Al Hilal",
            posicion = "Delantero",
            edad = 31,
            dorsal = 10
        )
        
        `when`(jugadorRepository.save(any())).thenReturn(savedJugador)

        // When
        val result = jugadorService.save(newJugador)

        // Then
        assert(result.idJugador == 3)
        assert(result.nombreJugador == "Neymar Jr")
        verify(jugadorRepository).save(newJugador)
    }

    @Test
    fun `delete should remove player when exists`() {
        // Given
        `when`(jugadorRepository.existsById(1)).thenReturn(true)
        doNothing().`when`(jugadorRepository).deleteById(1)

        // When
        jugadorService.delete(1)

        // Then
        verify(jugadorRepository).existsById(1)
        verify(jugadorRepository).deleteById(1)
    }

    @Test
    fun `delete should throw exception when player not found`() {
        // Given
        `when`(jugadorRepository.existsById(999)).thenReturn(false)

        // When & Then
        assertThrows<NoSuchElementException> {
            jugadorService.delete(999)
        }
        verify(jugadorRepository).existsById(999)
        verify(jugadorRepository, never()).deleteById(any())
    }
}
