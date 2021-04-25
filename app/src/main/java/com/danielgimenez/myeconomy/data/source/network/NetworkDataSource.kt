package com.danielgimenez.myeconomy.data.source.network

import android.content.Context
import android.net.ConnectivityManager
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.entity.exception.NetworkConnectionException
import com.danielgimenez.myeconomy.domain.usecase.charts.GetChartsRequest
import com.danielgimenez.myeconomy.domain.usecase.charts.GetChartsResponse
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseRequest
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseResponse
import com.danielgimenez.myeconomy.domain.usecase.login.GetDataForUserResponse
import com.danielgimenez.myeconomy.domain.usecase.types.InsertTypeRemoteResponse
import com.danielgimenez.myeconomy.domain.usecase.types.InsertTypeRemoteRequest
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class NetworkDataSource(
        private val context: Context,
        private val api: MyEconomyApi,
        private val fireStore: FireStoreDataSource): INetworkDataSource(){

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

    override suspend fun getDataForUser(id_token: String): Response<GetDataForUserResponse> {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val isConnected = cm.activeNetworkInfo.isConnected
        if(isConnected){
            try{
                val types = fireStore.getTypes(Firebase.auth.currentUser?.email!!)
                val expenses = fireStore.getExpenses(Firebase.auth.currentUser?.email!!)
                val new_user = fireStore.isNewUser(Firebase.auth.currentUser?.email!!)
                val response = GetDataForUserResponse(new_user, types, expenses)
                //val response = api.getDataForUser(id_token).await()
                return Response.Success(response)
            } catch (e: Exception){
                return Response.Error(e)
            }
        }
        else{
            return Response.Error(NetworkConnectionException())
        }
    }

    override suspend fun insertType(
        id_token: String,
        insertTypeRemoteRequest: InsertTypeRemoteRequest
    ): Response<InsertTypeRemoteResponse> {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val isConnected = cm.activeNetworkInfo.isConnected
        if(isConnected){
            try {
                val response = api.insertType(id_token, insertTypeRemoteRequest).await()
                return Response.Success(response)
            } catch (e: Exception){
                return Response.Error(e)
            }
        }
        else{
            return Response.Error(NetworkConnectionException())
        }
    }
}