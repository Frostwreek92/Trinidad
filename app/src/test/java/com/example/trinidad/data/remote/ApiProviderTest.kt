package com.example.trinidad.data.remote

import com.example.trinidad.data.remote.api.ApiFootballApi
import com.example.trinidad.data.remote.api.FootballDataApi
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Before
import org.junit.Test

class ApiProviderTest {

    @Before
    fun setup() {
        // Reset por si otros tests cambian el estado
        ApiProvider.currentApi = ApiType.FOOTBALL_DATA
    }

    @After
    fun tearDown() {
        ApiProvider.currentApi = ApiType.FOOTBALL_DATA
    }

    @Test
    fun `api football is lazily initialized and not null`() {
        val api = ApiProvider.apiFootball

        assertThat(api).isNotNull()
        assertThat(api).isInstanceOf(ApiFootballApi::class.java)
    }

    @Test
    fun `football data api is lazily initialized and not null`() {
        val api = ApiProvider.footballData

        assertThat(api).isNotNull()
        assertThat(api).isInstanceOf(FootballDataApi::class.java)
    }

    @Test
    fun `changing current api does not crash provider`() {
        ApiProvider.currentApi = ApiType.API_FOOTBALL

        val apiFootball = ApiProvider.apiFootball
        val footballData = ApiProvider.footballData

        assertThat(apiFootball).isNotNull()
        assertThat(footballData).isNotNull()
    }
}
