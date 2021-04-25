package com.danielgimenez.myeconomy.data.source.network

import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.domain.usecase.charts.GetChartsRequest
import com.danielgimenez.myeconomy.domain.usecase.charts.GetChartsResponse
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseRequest
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseResponse
import com.danielgimenez.myeconomy.domain.usecase.login.GetDataForUserResponse
import com.danielgimenez.myeconomy.domain.usecase.types.InsertTypeRemoteResponse
import com.danielgimenez.myeconomy.domain.usecase.types.InsertTypeRemoteRequest

abstract class INetworkDataSource {

    abstract suspend fun getCharts(request: GetChartsRequest): Response<GetChartsResponse>
    abstract suspend fun getDataForUser(id_token: String): Response<GetDataForUserResponse>
    abstract suspend fun insertType(id_token: String, insertTypeRemoteRequest: InsertTypeRemoteRequest): Response<InsertTypeRemoteResponse>
}