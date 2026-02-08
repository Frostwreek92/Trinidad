package com.example.trinidad.data.repository

import com.example.trinidad.data.remote.ApiProvider
import com.example.trinidad.data.remote.ApiType
import com.example.trinidad.data.remote.api.ApiFootballApi
import com.example.trinidad.data.remote.api.FootballDataApi
import com.example.trinidad.data.remote.dto.apifootball.TeamDto
import com.example.trinidad.data.remote.dto.apifootball.TeamResponseItemDto
import com.example.trinidad.data.remote.dto.apifootball.TeamsResponseDto
import com.example.trinidad.data.remote.dto.footballdata.TeamDto as FootballDataTeamDto
import com.example.trinidad.data.remote.dto.footballdata.AreaDto
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class TeamRepositoryImplTest {

    private lateinit var apiProvider: ApiProvider
    private lateinit var apiFootball: ApiFootballApi
    private lateinit var footballData: FootballDataApi
    private lateinit var repository: TeamRepositoryImpl

    @Before
    fun setup() {
        apiProvider = mockk(relaxed = true)
        apiFootball = mockk()
        footballData = mockk()

        every { apiProvider.apiFootball } returns apiFootball
        every { apiProvider.footballData } returns footballData

        repository = TeamRepositoryImpl(apiProvider)
    }

    @Test
    fun `getTeamsByLeague uses ApiFootball`() = runTest {
        every { apiProvider.currentApi } returns ApiType.API_FOOTBALL
        coEvery { apiFootball.getTeamsByLeague(any(), any()) } returns TeamsResponseDto(
            response = listOf(
                TeamResponseItemDto(
                    team = TeamDto(
                        id = 1,
                        name = "Barcelona",
                        logo = null
                    ),
                    venue = null
                )
            )
        )

        val result = repository.getTeamsByLeague(39)

        assertThat(result).hasSize(1)
        assertThat(result.first().name).isEqualTo("Barcelona")
    }

    @Test
    fun `getTeamDetail uses FootballData`() = runTest {
        every { apiProvider.currentApi } returns ApiType.FOOTBALL_DATA
        coEvery { footballData.getTeamDetail(any()) } returns FootballDataTeamDto(
            id = 10,
            name = "PSG",
            shortName = null,
            crest = null,
            venue = "Parc des Princes",
            area = AreaDto("France")
        )

        val result = repository.getTeamDetail(10)

        assertThat(result.name).isEqualTo("PSG")
        assertThat(result.city).isEqualTo("France")
    }
}
