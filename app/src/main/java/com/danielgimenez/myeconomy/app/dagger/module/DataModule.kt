package com.danielgimenez.myeconomy.app.dagger.module

import com.danielgimenez.myeconomy.ui.App
import com.danielgimenez.myeconomy.data.source.network.MyEconomyApi
import com.danielgimenez.myeconomy.data.source.network.NetworkDataSource
import dagger.Module
import dagger.Provides

@Module
class DataModule{

    @Provides
    fun provideNetworkDataSource(appContext: App, myEconomyApi: MyEconomyApi) = NetworkDataSource(appContext, myEconomyApi)
}