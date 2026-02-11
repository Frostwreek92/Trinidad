package com.example.trinidad.domain.usecase

import com.example.trinidad.domain.model.api.PlayerDetail
import com.example.trinidad.domain.repository.api.PlayerRepository
import com.example.trinidad.domain.usecase.api.GetPlayerDetailUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetPlayerDetailUseCaseTest {

    private lateinit var repository: PlayerRepository
    private lateinit var useCase: GetPlayerDetailUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetPlayerDetailUseCase(repository)
    }

    @Test
    fun `invoke returns player detail from repository`() = runTest {
        val detail = PlayerDetail(
            id = 10,
            name = "Messi",
            age = 36,
            nationality = "Argentina",
            position = "Forward",
            height = "170 cm",
            weight = "72 kg",
            photo = "photo"
        )

        coEvery { repository.getPlayerDetail(10) } returns detail

        val result = useCase(10)

        assertThat(result).isEqualTo(detail)
        coVerify { repository.getPlayerDetail(10) }
    }
}
