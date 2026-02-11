package com.example.jugadoresMySQL.model

import jakarta.persistence.*

@Entity
@Table(name = "jugadores_en_posicion_legendaria")
data class JugadorEnPosicion(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jugador_posicion")
    var idJugadorPosicion: Int? = null,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_jugador")
    var jugador: Jugador,

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "id_formacion")
    var formacion: Formacion,

    @Column(name = "posicion")
    var posicion: String = "",

    @Column(name = "posicion_x")
    var posicionX: Float = 0.0f,

    @Column(name = "posicion_y")
    var posicionY: Float = 0.0f
) {
    constructor() : this(null, Jugador(), Formacion(), "", 0.0f, 0.0f)
}
