package com.example.jugadoresMySQL.model

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "jugadores")
data class Jugador(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jugador")
    var idJugador: Int? = null,

    @Column(name = "nombre_jugador")
    var nombreJugador: String = "",

    @Column(name = "id_equipo")
    var idEquipo: Int = 0,

    @Column(name = "posicion")
    var posicion: String = "",

    @Column(name = "edad")
    var edad: Int = 0,

    @Column(name = "dorsal")
    var dorsal: Int = 0
) {
    // Constructor sin argumentos para JPA
    constructor() : this(null, "", "", "", 0, 0)
}
