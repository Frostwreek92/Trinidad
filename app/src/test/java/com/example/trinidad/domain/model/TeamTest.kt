package com.example.trinidad.domain.model

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class TeamTest {

    @Test
    fun `team is created correctly`() {
        val team = Team(
            id = 100,
            name = "FC Barcelona",
            logo = "logo"
        )

        assertThat(team.id).isEqualTo(100)
        assertThat(team.name).isEqualTo("FC Barcelona")
        assertThat(team.logo).isEqualTo("logo")
    }

    @Test
    fun `team copy works correctly`() {
        val team = Team(1, "Old Name", "logo")
        val updated = team.copy(name = "New Name")

        assertThat(updated.name).isEqualTo("New Name")
    }
}
