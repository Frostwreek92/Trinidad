package com.example.trinidad.domain.repository

import com.example.trinidad.domain.model.Player
import com.example.trinidad.domain.model.PlayerDetail
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import org.junit.Test

class PlayerRepositoryTest {

    private val fakeRepository = object : PlayerRepository {

        override suspend fun getPlayersByTeam(teamId: Int): List<Player> {
            return listOf(
                Player(
                    id = 10,
                    name = "Messi",
                    position = "Forward",
                    photo = "photo",
                    height = null,
                    weight = null,
                    nationality = "Argentina"
                )
            )
        }

        override suspend fun getPlayerDetail(playerId: Int): PlayerDetail {
            return PlayerDetail(
                id = playerId,
                name = "Messi",
                age = 36,
                nationality = "Argentina",
                position = "Forward",
                height = "170 cm",
                weight = "72 kg",
                photo = "photo"
            )
        }
    }

    @Test
    fun `player repository returns players by team`() = runTest {
        val result = fakeRepository.getPlayersByTeam(1)

        assertThat(result).hasSize(1)
        assertThat(result.first().name).isEqualTo("Messi")
    }

    @Test
    fun `player repository returns player detail`() = runTest {
        val result = fakeRepository.getPlayerDetail(10)

        assertThat(result.name).isEqualTo("Messi")
        assertThat(result.age).isEqualTo(36)
    }
}
