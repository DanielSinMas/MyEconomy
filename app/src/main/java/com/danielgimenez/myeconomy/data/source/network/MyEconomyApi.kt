package com.danielgimenez.myeconomy.data.source.network

import com.danielgimenez.myeconomy.domain.usecase.charts.GetChartsRequest
import com.danielgimenez.myeconomy.domain.usecase.charts.GetChartsResponse
import kotlinx.coroutines.Deferred
import retrofit2.http.Body
import retrofit2.http.POST

interface MyEconomyApi {
    companion object{
        const val URL_GET_CHARTS = "/get_charts"
    }

    @POST(URL_GET_CHARTS)
    fun getCharts(
        @Body getChartsRequest: GetChartsRequest
    ): Deferred<GetChartsResponse>
}