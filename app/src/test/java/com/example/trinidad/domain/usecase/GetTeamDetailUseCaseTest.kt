package com.example.trinidad.domain.usecase

import com.example.trinidad.domain.model.api.TeamDetail
import com.example.trinidad.domain.repository.api.TeamRepository
import com.example.trinidad.domain.usecase.api.GetTeamDetailUseCase
import com.google.common.truth.Truth.assertThat
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class GetTeamDetailUseCaseTest {

    private lateinit var repository: TeamRepository
    private lateinit var useCase: GetTeamDetailUseCase

    @Before
    fun setup() {
        repository = mockk()
        useCase = GetTeamDetailUseCase(repository)
    }

    @Test
    fun `invoke returns team detail from repository`() = runTest {
        val detail = TeamDetail(
            id = 1,
            name = "Barcelona",
            logo = "logo",
            stadium = "Camp Nou",
            city = "Barcelona",
            capacity = 99354,
            address = "direccion",
            surface = "superficie",
            stadiumImage = "stadiumImage"
        )

        coEvery { repository.getTeamDetail(1) } returns detail

        val result = useCase(1)

        assertThat(result).isEqualTo(detail)
        coVerify { repository.getTeamDetail(1) }
    }
}
