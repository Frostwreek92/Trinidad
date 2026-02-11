package com.example.trinidad.domain.model

import com.example.trinidad.domain.model.api.Player
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class PlayerTest {

    @Test
    fun `player is created correctly`() {
        val player = Player(
            id = 10,
            name = "Messi",
            position = "Forward",
            photo = "photo_url",
            height = "170 cm",
            weight = "72 kg",
            nationality = "Argentina"
        )

        assertThat(player.name).isEqualTo("Messi")
        assertThat(player.position).isEqualTo("Forward")
        assertThat(player.nationality).isEqualTo("Argentina")
    }

    @Test
    fun `player supports nullable optional fields`() {
        val player = Player(
            id = 20,
            name = "Unknown",
            position = "N/A",
            photo = "",
            height = null,
            weight = null,
            nationality = null
        )

        assertThat(player.height).isNull()
        assertThat(player.weight).isNull()
        assertThat(player.nationality).isNull()
    }
}
