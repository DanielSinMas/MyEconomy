package com.danielgimenez.myeconomy.data.repository

import android.content.Context
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.source.database.IDiskDataSource
import com.danielgimenez.myeconomy.data.source.network.INetworkDataSource
import com.danielgimenez.myeconomy.domain.usecase.login.GetDataForUserResponse

class LoginRepository(private val context: Context,
                      private val diskDataSource: IDiskDataSource,
                      private val networkDataSource: INetworkDataSource) {

    suspend fun getDataForUser(id_token: String): Response<GetDataForUserResponse> {
        return networkDataSource.getDataForUser(id_token)
    }
}