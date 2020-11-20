package com.danielgimenez.myeconomy.app.dagger

import com.danielgimenez.myeconomy.app.dagger.module.APIModule
import com.danielgimenez.myeconomy.app.dagger.module.ApplicationModule
import com.danielgimenez.myeconomy.app.dagger.module.DataModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class,
                    DataModule::class,
                    APIModule::class])
interface ApplicationComponent {

}