package com.example.jugadoresMySQL.model

import jakarta.persistence.*

@Entity
@Table(name = "equipos_por_liga")
data class EquipoPorLiga(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_equipo_liga")
    var idEquipoLiga: Int? = null,

    @ManyToOne
    @JoinColumn(name = "id_equipo")
    var equipo: Equipo? = null,

    @ManyToOne
    @JoinColumn(name = "id_liga")
    var liga: Liga? = null,

    @Column(name = "temporada")
    var temporada: String = "",

    @Column(name = "fecha_incorporacion")
    var fechaIncorporacion: Long = System.currentTimeMillis(),

    @Column(name = "activo")
    var activo: Boolean = true
) {
    constructor() : this(null, null, null, "", System.currentTimeMillis(), true)
}
