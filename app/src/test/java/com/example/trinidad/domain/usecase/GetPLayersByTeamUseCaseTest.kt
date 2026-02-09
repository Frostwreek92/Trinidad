package com.example.trinidad.domain.usecase

import com.example.trinidad.domain.model.Player
import com.example.trinidad.domain.repository.PlayerRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetPlayersByTeamUseCaseTest {

    private lateinit var repository: PlayerRepository
    private lateinit var useCase: GetPlayersByTeamUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetPlayersByTeamUseCase(repository)
    }

    @Test
    fun `invoke returns players by team from repository`() = runTest {
        val players = listOf(
            Player(
                id = 1,
                name = "Pedri",
                position = "Midfielder",
                photo = "photo",
                height = null,
                weight = null,
                nationality = "Spain"
            )
        )

        coEvery { repository.getPlayersByTeam(1) } returns players

        val result = useCase(1)

        assertThat(result).isEqualTo(players)
        coVerify { repository.getPlayersByTeam(1) }
    }
}
