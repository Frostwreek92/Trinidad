package com.example.trinidad.data.repository

import com.example.trinidad.data.remote.ApiProvider
import com.example.trinidad.data.remote.ApiType
import com.example.trinidad.data.remote.api.ApiFootballApi
import com.example.trinidad.data.remote.api.FootballDataApi
import com.example.trinidad.data.remote.dto.apifootball.PlayerDto
import com.example.trinidad.data.remote.dto.apifootball.PlayerResponseItemDto
import com.example.trinidad.data.remote.dto.apifootball.PlayersResponseDto
import com.example.trinidad.data.remote.dto.footballdata.SquadPlayerDto
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test


class PlayerRepositoryImplTest {

    private lateinit var apiProvider: ApiProvider
    private lateinit var apiFootball: ApiFootballApi
    private lateinit var footballData: FootballDataApi
    private lateinit var repository: PlayerRepositoryImpl

    @Before
    fun setup() {
        apiProvider = mockk(relaxed = true)
        apiFootball = mockk()
        footballData = mockk()

        every { apiProvider.apiFootball } returns apiFootball
        every { apiProvider.footballData } returns footballData

        repository = PlayerRepositoryImpl(apiProvider)
    }

    @Test
    fun `getPlayersByTeam uses ApiFootball`() = runTest {
        every { apiProvider.currentApi } returns ApiType.API_FOOTBALL
        coEvery { apiFootball.getPlayersByTeam(any(), any()) } returns PlayersResponseDto(
            response = listOf(
                PlayerResponseItemDto(
                    player = PlayerDto(
                        id = 10,
                        name = "Messi",
                        firstname = null,
                        lastname = null,
                        age = null,
                        nationality = null,
                        height = null,
                        weight = null,
                        photo = null
                    ),
                    statistics = emptyList()
                )
            )
        )

        val result = repository.getPlayersByTeam(1)

        assertThat(result).hasSize(1)
        assertThat(result.first().name).isEqualTo("Messi")
    }

    @Test
    fun `getPlayerDetail uses FootballData`() = runTest {
        every { apiProvider.currentApi } returns ApiType.FOOTBALL_DATA
        coEvery { footballData.getPlayer(any()) } returns SquadPlayerDto(
            id = 7,
            name = "Cristiano",
            position = "Forward",
            dateOfBirth = null,
            nationality = "Portugal"
        )

        val result = repository.getPlayerDetail(7)

        assertThat(result.name).isEqualTo("Cristiano")
        assertThat(result.position).isEqualTo("Forward")
    }
}
