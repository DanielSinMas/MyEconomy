package com.danielgimenez.myeconomy.data.source.network

import android.content.Context
import android.net.ConnectivityManager
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.entity.exception.NetworkConnectionException
import com.danielgimenez.myeconomy.domain.usecase.charts.GetChartsRequest
import com.danielgimenez.myeconomy.domain.usecase.charts.GetChartsResponse

class NetworkDataSource(private val context: Context, private val api: MyEconomyApi): INetworkDataSource(){

    override suspend fun getCharts(request: GetChartsRequest): Response<GetChartsResponse> {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val isConnected = cm.activeNetworkInfo.isConnected
        /*if(isConnected) {
            try {
                return Response.Success(api.getCharts(request).await())
            } catch (e: Exception) {
                return Response.Error(e)
            }
        }
        else{
            return Response.Error(NetworkConnectionException())
        }*/
        return Response.Success(GetChartsResponse(""))
    }


}