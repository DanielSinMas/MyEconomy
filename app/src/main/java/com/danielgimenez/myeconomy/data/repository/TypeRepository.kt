package com.danielgimenez.myeconomy.data.repository

import android.content.Context
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.source.database.IDiskDataSource
import com.danielgimenez.myeconomy.data.source.network.INetworkDataSource
import com.danielgimenez.myeconomy.domain.model.Type
import com.danielgimenez.myeconomy.domain.usecase.types.InsertTypeRemoteRequest
import com.danielgimenez.myeconomy.domain.usecase.types.InsertTypeRemoteResponse
import com.danielgimenez.myeconomy.domain.usecase.types.InsertTypeRequest
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class TypeRepository(private val context: Context,
                     private val diskDataSource: IDiskDataSource,
                     private val networkDataSource: INetworkDataSource){

    fun getTypes(): List<Type>? {
        return diskDataSource.getTypes()?.map { it.toModel() }
    }

    fun insertType(request: InsertTypeRequest): Response.Success<Type> {
        val result = diskDataSource.insertType(request.type.toEntity())
        return Response.Success(request.type)
    }

    suspend fun insertTypeRemote(request: InsertTypeRemoteRequest): Response<InsertTypeRemoteResponse>{
        val actualTypes = getTypes()
        val typesToUpdate: List<Type> = ArrayList()
        val typesToInsert: List<Type> = ArrayList()
        request.types.map { type ->
            typesToUpdate.plus(actualTypes?.map { it.localId == type.localId })
            typesToInsert.plus(actualTypes?.map { it.localId != type.localId })
            /*if(actualTypes?.map { it.localId }?.contains(type.localId) == true && !actualTypes.map { it.name }.contains(type.name)){
                typesToUpdate.plus(type)
            }
            if(actualTypes?.map { it.name }?.contains(type.name) == false){
                typesToInsert.plus(type)
            }*/
        }
        val sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val token = sharedPref.getString("token", null)
        if(token != null) {
            val response = networkDataSource.insertType(token!!, request)
            if(response is Response.Success){
                val insertLocallyResponse = response.data.types.map { type ->
                    insertType(InsertTypeRequest(type))
                }
                return response
            }
        }
        else{
            return Response.Error(Exception())
        }
    }
}
