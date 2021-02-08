package com.danielgimenez.myeconomy.data.repository

import android.content.Context
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.source.database.IDiskDataSource
import com.danielgimenez.myeconomy.data.source.network.INetworkDataSource
import com.danielgimenez.myeconomy.domain.usecase.charts.GetChartsRequest
import com.danielgimenez.myeconomy.domain.usecase.charts.GetChartsResponse

class ChartsRepository(private val context: Context,
                       private val diskDataSource: IDiskDataSource,
                       private val networkDataSource: INetworkDataSource): BaseRepository(){

       suspend fun getCharts(request: GetChartsRequest): Response<GetChartsResponse> {
           return networkDataSource.getCharts(request)
       }
}