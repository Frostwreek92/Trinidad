package com.example.trinidad.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class PlayerDetailTest {

    @Test
    fun `player detail is created correctly`() {
        val detail = PlayerDetail(
            id = 7,
            name = "Cristiano",
            age = 39,
            nationality = "Portugal",
            position = "Forward",
            height = "187 cm",
            weight = "83 kg",
            photo = "photo"
        )

        assertThat(detail.age).isEqualTo(39)
        assertThat(detail.position).isEqualTo("Forward")
        assertThat(detail.nationality).isEqualTo("Portugal")
    }

    @Test
    fun `player detail equality works as expected`() {
        val p1 = PlayerDetail(1, "A", 20, "X", "Mid", "170", "70", "")
        val p2 = PlayerDetail(1, "A", 20, "X", "Mid", "170", "70", "")

        assertThat(p1).isEqualTo(p2)
    }
}
