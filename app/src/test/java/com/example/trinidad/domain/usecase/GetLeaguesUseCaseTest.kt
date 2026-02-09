package com.example.trinidad.domain.usecase

import com.example.trinidad.domain.model.League
import com.example.trinidad.domain.repository.LeagueRepository
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetLeaguesUseCaseTest {

    private lateinit var repository: LeagueRepository
    private lateinit var useCase: GetLeaguesUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetLeaguesUseCase(repository)
    }

    @Test
    fun `invoke returns leagues from repository`() = runTest {
        val leagues = listOf(
            League(1, "Premier League", "England", "logo")
        )

        coEvery { repository.getLeagues() } returns leagues

        val result = useCase()

        assertThat(result).isEqualTo(leagues)
        coVerify(exactly = 1) { repository.getLeagues() }
    }
}
