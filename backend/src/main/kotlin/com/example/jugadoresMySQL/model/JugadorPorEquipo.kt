package com.example.jugadoresMySQL.model

import jakarta.persistence.*

@Entity
@Table(name = "jugadores_por_equipo")
data class JugadorPorEquipo(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_jugador_equipo")
    var idJugadorEquipo: Int? = null,

    @ManyToOne
    @JoinColumn(name = "id_jugador")
    var jugador: Jugador? = null,

    @ManyToOne
    @JoinColumn(name = "id_equipo")
    var equipo: Equipo? = null,

    @Column(name = "dorsal")
    var dorsal: Int = 0
) {
    constructor() : this(null, null, null, 0)
}
