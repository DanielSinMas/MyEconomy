package com.danielgimenez.myeconomy.data.repository

import android.content.Context
import com.danielgimenez.myeconomy.data.source.database.IDiskDataSource
import com.danielgimenez.myeconomy.data.source.network.INetworkDataSource
import com.danielgimenez.myeconomy.domain.model.Type

class TypeRepository(private val context: Context,
                     private val diskDataSource: IDiskDataSource,
                     private val networkDataSource: INetworkDataSource){

    suspend fun getTypes(): List<Type>? {
        return diskDataSource.getTypes()?.map { it.toModel() }
    }
}
