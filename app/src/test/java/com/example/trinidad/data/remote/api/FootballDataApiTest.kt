package com.example.trinidad.data.remote.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FootballDataApiTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var api: FootballDataApi

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()

        api = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FootballDataApi::class.java)
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `getCompetitions sends correct request`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("""{ "competitions": [] }""")
        )

        api.getCompetitions()
        val request = mockWebServer.takeRequest()

        assertThat(request.path).isEqualTo("/competitions")
    }

    @Test
    fun `getTeamsByCompetition uses path parameter`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("""{ "teams": [] }""")
        )

        api.getTeamsByCompetition(competitionId = 2001)
        val request = mockWebServer.takeRequest()

        assertThat(request.path).isEqualTo("/competitions/2001/teams")
    }

    @Test
    fun `getPlayer uses correct path`() = runTest {
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("""{ "id": 10, "name": "Messi" }""")
        )

        api.getPlayer(playerId = 10)
        val request = mockWebServer.takeRequest()

        assertThat(request.path).isEqualTo("/persons/10")
    }
}
