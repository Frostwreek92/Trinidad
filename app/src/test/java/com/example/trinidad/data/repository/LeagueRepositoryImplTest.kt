package com.example.trinidad.data.repository

import com.example.trinidad.data.remote.ApiProvider
import com.example.trinidad.data.remote.ApiType
import com.example.trinidad.data.remote.api.ApiFootballApi
import com.example.trinidad.data.remote.api.FootballDataApi
import com.example.trinidad.data.remote.dto.apifootball.*
import com.example.trinidad.data.remote.dto.footballdata.*
import com.google.common.truth.Truth.assertThat
import io.mockk.*
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class LeagueRepositoryImplTest {

    private lateinit var apiProvider: ApiProvider
    private lateinit var apiFootball: ApiFootballApi
    private lateinit var footballData: FootballDataApi
    private lateinit var repository: LeagueRepositoryImpl

    @Before
    fun setup() {
        apiProvider = mockk(relaxed = true)
        apiFootball = mockk()
        footballData = mockk()

        every { apiProvider.apiFootball } returns apiFootball
        every { apiProvider.footballData } returns footballData

        repository = LeagueRepositoryImpl(apiProvider)
    }

    @After
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `getLeagues uses ApiFootball when currentApi is API_FOOTBALL`() = runTest {
        every { apiProvider.currentApi } returns ApiType.API_FOOTBALL
        coEvery { apiFootball.getLeagues() } returns LeaguesResponseDto(
            response = listOf(
                LeagueDto(
                    league = LeagueInfoDto(1, "Premier League", null),
                    country = CountryDto("England")
                )
            )
        )

        val result = repository.getLeagues()

        assertThat(result).hasSize(1)
        assertThat(result.first().name).isEqualTo("Premier League")
    }

    @Test
    fun `getLeagues uses FootballData when currentApi is FOOTBALL_DATA`() = runTest {
        every { apiProvider.currentApi } returns ApiType.FOOTBALL_DATA
        coEvery { footballData.getCompetitions() } returns CompetitionsResponseDto(
            count = 1,
            competitions = listOf(
                CompetitionDto(
                    id = 2001,
                    name = "La Liga",
                    emblem = null,
                    area = AreaDto("Spain")
                )
            )
        )

        val result = repository.getLeagues()

        assertThat(result).hasSize(1)
        assertThat(result.first().name).isEqualTo("La Liga")
    }
}
