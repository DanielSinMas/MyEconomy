package com.danielgimenez.myeconomy.app.dagger.module

import com.danielgimenez.myeconomy.data.repository.ExpenseRepository
import com.danielgimenez.myeconomy.data.repository.TypeRepository
import com.danielgimenez.myeconomy.data.source.database.IDiskDataSource
import com.danielgimenez.myeconomy.data.source.network.INetworkDataSource
import com.danielgimenez.myeconomy.ui.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule{

    @Provides
    @Singleton
    fun provideExpenseRepository(context: App,
                                diskDataSource: IDiskDataSource,
                                networkDataSource: INetworkDataSource) = ExpenseRepository(context, diskDataSource, networkDataSource)

    @Provides
    @Singleton
    fun provideTypeRepository(context: App,
                            diskDataSource: IDiskDataSource,
                            networkDataSource: INetworkDataSource) = TypeRepository(context, diskDataSource, networkDataSource)
}