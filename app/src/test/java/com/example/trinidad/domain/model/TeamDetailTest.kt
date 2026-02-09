package com.example.trinidad.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TeamDetailTest {

    @Test
    fun `team detail is created correctly`() {
        val detail = TeamDetail(
            id = 1,
            name = "Real Madrid",
            logo = "logo",
            stadium = "Bernabeu",
            city = "Madrid",
            capacity = 81044
        )

        assertThat(detail.stadium).isEqualTo("Bernabeu")
        assertThat(detail.city).isEqualTo("Madrid")
        assertThat(detail.capacity).isEqualTo(81044)
    }

    @Test
    fun `team detail equality works correctly`() {
        val t1 = TeamDetail(1, "A", "logo", "stadium", "city", 100)
        val t2 = TeamDetail(1, "A", "logo", "stadium", "city", 100)

        assertThat(t1).isEqualTo(t2)
    }
}
