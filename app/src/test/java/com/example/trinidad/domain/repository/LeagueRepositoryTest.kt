package com.example.trinidad.domain.repository

import com.example.trinidad.domain.model.League
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test

class LeagueRepositoryTest {

    private val fakeRepository = object : LeagueRepository {
        override suspend fun getLeagues(): List<League> {
            return listOf(
                League(
                    id = 1,
                    name = "Premier League",
                    country = "England",
                    logo = "logo"
                )
            )
        }
    }

    @Test
    fun `league repository returns list of leagues`() = runTest {
        val result = fakeRepository.getLeagues()

        assertThat(result).hasSize(1)
        assertThat(result.first().name).isEqualTo("Premier League")
    }
}
