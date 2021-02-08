package com.danielgimenez.myeconomy.data.source.network

import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.domain.usecase.charts.GetChartsRequest
import com.danielgimenez.myeconomy.domain.usecase.charts.GetChartsResponse

abstract class INetworkDataSource {

    //abstract suspend fun getExpense()
    abstract suspend fun getCharts(request: GetChartsRequest): Response<GetChartsResponse>
}