package com.danielgimenez.myeconomy.app.dagger

import com.danielgimenez.myeconomy.app.dagger.module.*
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
                    DomainModule::class,
                    RepositoryModule::class,
                    UtilsModule::class])
interface ApplicationComponent {
    fun plus(module: FormularyFragmentModule): FormularyFragmentComponent
}