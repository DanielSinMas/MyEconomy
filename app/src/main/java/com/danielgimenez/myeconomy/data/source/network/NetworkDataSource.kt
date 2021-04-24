package com.danielgimenez.myeconomy.data.source.network

import android.content.Context
import android.net.ConnectivityManager
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.entity.exception.NetworkConnectionException
import com.danielgimenez.myeconomy.domain.model.ExpenseResponse
import com.danielgimenez.myeconomy.domain.model.Type
import com.danielgimenez.myeconomy.domain.usecase.charts.GetChartsRequest
import com.danielgimenez.myeconomy.domain.usecase.charts.GetChartsResponse
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseRequest
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseResponse
import com.danielgimenez.myeconomy.domain.usecase.login.GetDataForUserResponse
import com.danielgimenez.myeconomy.domain.usecase.types.InsertTypeRemoteResponse
import com.danielgimenez.myeconomy.domain.usecase.types.InsertTypeRemoteRequest
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import java.lang.Exception
import java.util.concurrent.ExecutionException
import java.util.concurrent.TimeUnit

class NetworkDataSource(private val context: Context, private val api: MyEconomyApi): INetworkDataSource(){

    private val TYPES_COLLECTION = "types"
    private val EXPENSES_COLLECTION = "expenses"
    private val USERS_COLLECTION = "users"

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
                val types = getTypes(Firebase.auth.currentUser?.email!!)
                val expenses = getExpenses(Firebase.auth.currentUser?.email!!)
                val new_user = isNewUser(Firebase.auth.currentUser?.email!!)
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

    override suspend fun insertExpense(id_token: String, insertExpenseRequest: InsertExpenseRequest): Response<InsertExpenseResponse> {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val isConnected = cm.activeNetworkInfo.isConnected
        if(isConnected){
            try {
                val response = api.insertExpense(id_token, insertExpenseRequest).await()
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

    private fun getTypes(email: String): ArrayList<Type> {
        val db = Firebase.firestore
        val types = ArrayList<Type>()
        try {
            val task = Tasks.await(db.collection(TYPES_COLLECTION)
                    .whereEqualTo("user", email)
                    .get())

            if (!task.isEmpty) {
                for (document in task.documents) {
                    val localId = document.getLong(Type.localIdField)!!.toInt()
                    types.add(Type(localId, document.getString(Type.nameField)!!, localId))
                }
            }
            else{
                throw Exception()
            }
        }
        catch (e: ExecutionException){
            throw e
        }
        return types
    }

    private fun getExpenses(email: String): ArrayList<ExpenseResponse>{
        val db = Firebase.firestore
        val expenses = ArrayList<ExpenseResponse>()
        try{
            val task = Tasks.await(db.collection(EXPENSES_COLLECTION)
                    .whereEqualTo("user", email)
                    .get())
            if(!task.isEmpty){
                for(document in task.documents){
                    val amount = document.getDouble("amount")!!.toFloat()
                    val description = document.getString("description")!!
                    val type = document.getLong("type")!!.toInt()
                    val date = document.getString("date")!!
                    expenses.add(ExpenseResponse(amount, description, type, date))
                }
            }
        } catch (e: ExecutionException){
            throw e
        }
        return expenses
    }

    private fun isNewUser(email: String): Boolean {
        val db = Firebase.firestore
        val new_user: Boolean
        try{
            val task = Tasks.await(db.collection(USERS_COLLECTION)
                    .whereEqualTo("email", email)
                    .get())
            if(task.isEmpty) new_user = true
            else new_user = false
        } catch (e: ExecutionException){
            throw e
        }
        return new_user
    }


}