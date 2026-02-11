package com.example.trinidad.domain.model

import com.example.trinidad.domain.model.api.League
import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LeagueTest {

    @Test
    fun `league is created correctly`() {
        val league = League(
            id = 1,
            name = "Premier League",
            country = "England",
            logo = "logo_url"
        )

        assertThat(league.id).isEqualTo(1)
        assertThat(league.name).isEqualTo("Premier League")
        assertThat(league.country).isEqualTo("England")
        assertThat(league.logo).isEqualTo("logo_url")
    }

    @Test
    fun `league copy creates a new instance with modified values`() {
        val original = League(1, "La Liga", "Spain", "logo")
        val copy = original.copy(name = "Liga EA Sports")

        assertThat(copy.name).isEqualTo("Liga EA Sports")
        assertThat(copy.country).isEqualTo("Spain")
    }
}
