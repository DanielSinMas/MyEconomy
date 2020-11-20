package com.danielgimenez.myeconomy.app.dagger.module

import com.danielgimenez.myeconomy.ui.App
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ApplicationModule(val app: App){

    @Provides
    @Singleton
    fun provideApp(): App = app
}