package com.example.trinidad.data.remote

import com.example.trinidad.data.remote.api.FootballApi
import com.example.trinidad.data.remote.network.FootballNetworkModule
import com.example.trinidad.data.remote.network.OtherFootballNetworkModule

object ApiProvider {

    private var currentApi: ApiType = ApiType.API_FOOTBALL

    fun set(api: ApiType) {
        currentApi = api
    }

    fun provideFootballApi(): FootballApi {
        return when (currentApi) {
            ApiType.API_FOOTBALL -> FootballNetworkModule.provideApi()
            ApiType.OTRA_API -> OtherFootballNetworkModule.provideApi()
        }
    }
}
