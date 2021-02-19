package com.danielgimenez.myeconomy.data.repository

import android.content.Context
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.source.database.IDiskDataSource
import com.danielgimenez.myeconomy.data.source.network.INetworkDataSource
import com.danielgimenez.myeconomy.domain.model.Type
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
}
