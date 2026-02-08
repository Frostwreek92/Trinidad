package com.example.trinidad.data.remote.network

import com.example.trinidad.data.remote.api.FootballDataApi
import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.test.runTest
import okhttp3.OkHttpClient
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FootballDataNetworkModuleTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun `football data client adds auth token header`() = runTest {
        // Arrange
        mockWebServer.enqueue(
            MockResponse()
                .setResponseCode(200)
                .setBody("""{ "count": 0, "competitions": [] }""")
        )

        val okHttpClient = FootballDataNetworkModule
            .javaClass
            .getDeclaredMethod("provideOkHttpClient")
            .apply { isAccessible = true }
            .invoke(FootballDataNetworkModule) as OkHttpClient

        val api: FootballDataApi = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(FootballDataApi::class.java)

        // Act
        api.getCompetitions()
        val request = mockWebServer.takeRequest()

        // Assert
        assertThat(request.getHeader("X-Auth-Token")).isNotNull()
    }
}
