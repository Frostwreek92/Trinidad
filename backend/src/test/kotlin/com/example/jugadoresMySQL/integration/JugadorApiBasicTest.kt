package com.example.jugadoresMySQL.integration

import com.example.jugadoresMySQL.model.Jugador
import com.example.jugadoresMySQL.repository.JugadorRepository
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.web.server.LocalServerPort
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.TestPropertySource
import org.springframework.web.client.RestTemplate
import org.springframework.http.HttpStatus

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
@TestPropertySource(properties = [
    "spring.datasource.url=jdbc:h2:mem:testdb",
    "spring.datasource.driver-class-name=org.h2.Driver",
    "spring.jpa.hibernate.ddl-auto=create-drop",
    "spring.jpa.database-platform=org.hibernate.dialect.H2Dialect"
])
class JugadorApiBasicTest {

    @Autowired
    private lateinit var jugadorRepository: JugadorRepository

    @LocalServerPort
    private var port: Int = 0

    private val restTemplate = RestTemplate()
    private val baseUrl: String get() = "http://localhost:$port/api/jugadores"

    @BeforeEach
    fun setUp() {
        jugadorRepository.deleteAll()
    }

    @Test
    fun `should create player`() {
        // Given
        val newJugador = Jugador(
            nombreJugador = "Lionel Messi",
            nombreEquipo = "PSG",
            posicion = "Delantero",
            edad = 35,
            dorsal = 10
        )

        // When
        val response = restTemplate.postForEntity(baseUrl, newJugador, Jugador::class.java)

        // Then
        assert(response.statusCode == HttpStatus.CREATED)
        val createdJugador = response.body!!
        assert(createdJugador.idJugador != null)
        assert(createdJugador.nombreJugador == "Lionel Messi")
    }

    @Test
    fun `should get all players`() {
        // Given
        val jugadores = listOf(
            Jugador(nombreJugador = "Lionel Messi", nombreEquipo = "PSG", posicion = "Delantero", edad = 35, dorsal = 10),
            Jugador(nombreJugador = "Cristiano Ronaldo", nombreEquipo = "Al Nassr", posicion = "Delantero", edad = 37, dorsal = 7)
        )

        jugadorRepository.saveAll(jugadores)

        // When
        val response = restTemplate.getForEntity(baseUrl, Array<Jugador>::class.java)

        // Then
        assert(response.statusCode == HttpStatus.OK)
        val jugadoresResponse = response.body!!
        assert(jugadoresResponse.size == 2)
        assert(jugadoresResponse[0].nombreJugador == "Lionel Messi")
        assert(jugadoresResponse[1].nombreJugador == "Cristiano Ronaldo")
    }

    @Test
    fun `should get player by id`() {
        // Given
        val jugador = jugadorRepository.save(
            Jugador(nombreJugador = "Lionel Messi", nombreEquipo = "PSG", posicion = "Delantero", edad = 35, dorsal = 10)
        )

        // When
        val response = restTemplate.getForEntity("$baseUrl/${jugador.idJugador}", Jugador::class.java)

        // Then
        assert(response.statusCode == HttpStatus.OK)
        val retrievedJugador = response.body!!
        assert(retrievedJugador.nombreJugador == "Lionel Messi")
        assert(retrievedJugador.nombreEquipo == "PSG")
    }

    @Test
    fun `should return 404 for non-existent player`() {
        // When
        try {
            restTemplate.getForEntity("$baseUrl/999", String::class.java)
            assert(false) { "Debería haber lanzado excepción 404" }
        } catch (e: org.springframework.web.client.HttpClientErrorException) {
            // Then
            assert(e.statusCode == HttpStatus.NOT_FOUND)
        }
    }

    @Test
    fun `should get statistics`() {
        // Given
        val jugadores = listOf(
            Jugador(nombreJugador = "Lionel Messi", nombreEquipo = "PSG", posicion = "Delantero", edad = 35, dorsal = 10),
            Jugador(nombreJugador = "Cristiano Ronaldo", nombreEquipo = "Al Nassr", posicion = "Delantero", edad = 37, dorsal = 7),
            Jugador(nombreJugador = "Jude Bellingham", nombreEquipo = "Real Madrid", posicion = "Centrocampista", edad = 20, dorsal = 5)
        )

        jugadorRepository.saveAll(jugadores)

        // When
        val response = restTemplate.getForEntity("$baseUrl/estadisticas", Map::class.java)

        // Then
        assert(response.statusCode == HttpStatus.OK)
        val estadisticas = response.body!!
        
        // Debug: imprimir valores
        println("Estadísticas recibidas:")
        estadisticas.forEach { (key, value) ->
            println("$key: $value (tipo: ${value?.javaClass?.simpleName})")
        }
        
        // Comparar con diferentes tipos
        val totalJugadores = estadisticas["totalJugadores"]
        val totalEquipos = estadisticas["totalEquipos"]
        val jugadoresJovenes = estadisticas["jugadoresJovenes"]
        val jugadoresVeteranos = estadisticas["jugadoresVeteranos"]
        val jugadoresExpertos = estadisticas["jugadoresExpertos"]
        
        // Corregir las expectativas según la lógica del controlador:
        // - Jovenes: 18-25 años (solo Bellingham)
        // - Veteranos: 26-30 años (nadie)
        // - Expertos: >30 años (Messi 35, Ronaldo 37)
        
        assert(totalJugadores == 3 || totalJugadores == 3.0 || totalJugadores == 3L) { "totalJugadores esperado 3, recibido $totalJugadores" }
        assert(totalEquipos == 3 || totalEquipos == 3.0 || totalEquipos == 3L) { "totalEquipos esperado 3, recibido $totalEquipos" }
        assert(jugadoresJovenes == 1 || jugadoresJovenes == 1.0 || jugadoresJovenes == 1L) { "jugadoresJovenes esperado 1, recibido $jugadoresJovenes" }
        assert(jugadoresVeteranos == 0 || jugadoresVeteranos == 0.0 || jugadoresVeteranos == 0L) { "jugadoresVeteranos esperado 0, recibido $jugadoresVeteranos" }
        assert(jugadoresExpertos == 2 || jugadoresExpertos == 2.0 || jugadoresExpertos == 2L) { "jugadoresExpertos esperado 2, recibido $jugadoresExpertos" }
    }

    @Test
    fun `should return empty list when no players exist`() {
        // When
        val response = restTemplate.getForEntity(baseUrl, Array<Jugador>::class.java)

        // Then
        assert(response.statusCode == HttpStatus.OK)
        val jugadores = response.body!!
        assert(jugadores.isEmpty())
    }
}
