package com.example.jugadoresMySQL.model

import jakarta.persistence.*

@Entity
@Table(name = "formacion_legendaria")
data class Formacion(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_formacion")
    var idFormacion: Int? = null,

    @Column(name = "esquema")
    var esquema: String = "4-3-3",

    @Column(name = "fecha_creacion")
    var fechaCreacion: Long = System.currentTimeMillis(),

    @OneToMany(mappedBy = "formacion", cascade = [CascadeType.ALL], fetch = FetchType.EAGER)
    var jugadores: List<JugadorEnPosicion> = emptyList()
) {
    constructor() : this(null, "4-3-3", System.currentTimeMillis(), emptyList())
}
