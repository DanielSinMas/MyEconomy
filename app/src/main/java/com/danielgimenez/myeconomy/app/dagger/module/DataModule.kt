package com.danielgimenez.myeconomy.app.dagger.module

import com.danielgimenez.myeconomy.data.source.database.DiskDataSource
import com.danielgimenez.myeconomy.data.source.database.IDiskDataSource
import com.danielgimenez.myeconomy.data.source.network.FireStoreDataSource
import com.danielgimenez.myeconomy.data.source.network.INetworkDataSource
import com.danielgimenez.myeconomy.ui.App
import com.danielgimenez.myeconomy.data.source.network.MyEconomyApi
import com.danielgimenez.myeconomy.data.source.network.NetworkDataSource
import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class DataModule{

    @Provides
    fun provideNetworkDataSource(
            appContext: App,
            myEconomyApi: MyEconomyApi,
            fireStore: FireStoreDataSource) = NetworkDataSource(appContext, myEconomyApi, fireStore) as INetworkDataSource

    @Provides
    @Singleton
    fun provideIDiskDataSource(appContext: App) = DiskDataSource(appContext) as IDiskDataSource

    @Provides
    @Singleton
    fun provideFireStoreDataSource() = FireStoreDataSource()
}