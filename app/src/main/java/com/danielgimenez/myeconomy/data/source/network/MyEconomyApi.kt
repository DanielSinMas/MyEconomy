package com.danielgimenez.myeconomy.data.source.network

import com.danielgimenez.myeconomy.domain.usecase.charts.GetChartsRequest
import com.danielgimenez.myeconomy.domain.usecase.charts.GetChartsResponse
import com.danielgimenez.myeconomy.domain.usecase.login.GetDataForUserResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface MyEconomyApi {
    companion object{
        const val URL_GET_CHARTS = "/get_charts"
        const val URL_GET_DATA_USER = "/user"

        const val HEADER_USER = "id_token"
    }

    @POST(URL_GET_CHARTS)
    fun getCharts(
        @Body getChartsRequest: GetChartsRequest
    ): Deferred<GetChartsResponse>

    @GET(URL_GET_DATA_USER)
    fun getDataForUser(
        @Header(HEADER_USER) id_token: String
    ): Deferred<GetDataForUserResponse>
}