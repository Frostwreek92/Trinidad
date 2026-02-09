package com.example.trinidad.domain.usecase

import com.example.trinidad.domain.model.Team
import com.example.trinidad.domain.repository.TeamRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetTeamsByLeagueUseCaseTest {

    private lateinit var repository: TeamRepository
    private lateinit var useCase: GetTeamsByLeagueUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetTeamsByLeagueUseCase(repository)
    }

    @Test
    fun `invoke returns teams from repository`() = runTest {
        val teams = listOf(
            Team(
                id = 10,
                name = "PSG",
                logo = "logo"
            )
        )

        coEvery { repository.getTeamsByLeague(39) } returns teams

        val result = useCase(39)

        assertThat(result).isEqualTo(teams)
        coVerify { repository.getTeamsByLeague(39) }
    }
}
