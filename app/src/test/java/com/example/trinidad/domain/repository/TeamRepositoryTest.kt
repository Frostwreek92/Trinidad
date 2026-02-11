package com.example.trinidad.domain.repository

import com.example.trinidad.domain.model.Team
import com.example.trinidad.domain.model.TeamDetail
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test

class TeamRepositoryTest {

    private val fakeRepository = object : TeamRepository {

        override suspend fun getTeamsByLeague(leagueId: Int): List<Team> {
            return listOf(
                Team(
                    id = 100,
                    name = "FC Barcelona",
                    logo = "logo"
                )
            )
        }

        override suspend fun getTeamDetail(teamId: Int): TeamDetail {
            return TeamDetail(
                id = teamId,
                name = "FC Barcelona",
                logo = "logo",
                stadium = "Camp Nou",
                city = "Barcelona",
                capacity = 99354,
                address = "Adress",
                surface = "Superficie",
                stadiumImage = "stadiumImage"
            )
        }
    }

    @Test
    fun `team repository returns teams by league`() = runTest {
        val result = fakeRepository.getTeamsByLeague(39)

        assertThat(result).hasSize(1)
        assertThat(result.first().name).isEqualTo("FC Barcelona")
    }

    @Test
    fun `team repository returns team detail`() = runTest {
        val result = fakeRepository.getTeamDetail(100)

        assertThat(result.stadium).isEqualTo("Camp Nou")
        assertThat(result.capacity).isEqualTo(99354)
    }
}
