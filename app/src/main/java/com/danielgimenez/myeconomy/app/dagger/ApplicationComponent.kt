package com.danielgimenez.myeconomy.app.dagger

import com.danielgimenez.myeconomy.app.dagger.module.APIModule
import com.danielgimenez.myeconomy.app.dagger.module.ApplicationModule
import com.danielgimenez.myeconomy.app.dagger.module.DataModule
import com.danielgimenez.myeconomy.app.dagger.module.UtilsModule
import com.danielgimenez.myeconomy.app.dagger.subcomponent.formulary.FormularyFragmentComponent
import com.danielgimenez.myeconomy.app.dagger.subcomponent.formulary.FormularyFragmentModule
import com.danielgimenez.myeconomy.app.dagger.viewmodel.ViewModelFactoryModule
import com.danielgimenez.myeconomy.app.dagger.viewmodel.ViewModelModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class,
                    DataModule::class,
                    APIModule::class,
                    ViewModelModule::class,
                    ViewModelFactoryModule::class,
                    UtilsModule::class])
interface ApplicationComponent {
    fun plus(module: FormularyFragmentModule): FormularyFragmentComponent
}