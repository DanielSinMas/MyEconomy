package com.danielgimenez.myeconomy.app.dagger.viewmodel

import androidx.lifecycle.ViewModel
import com.danielgimenez.myeconomy.ui.viewmodel.ChartsViewModel
import com.danielgimenez.myeconomy.ui.viewmodel.FormularyViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

@Target(AnnotationTarget.FUNCTION, AnnotationTarget.PROPERTY_GETTER, AnnotationTarget.PROPERTY_SETTER)
@MapKey
internal annotation class ViewModelKey(val value: KClass<out ViewModel>)

@Module
abstract class ViewModelModule{

    @Binds
    @IntoMap
    @ViewModelKey(FormularyViewModel::class)
    abstract fun bindFormularyViewModel(viewModel: FormularyViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ChartsViewModel::class)
    abstract fun bindChartsViewModel(viewModel: ChartsViewModel): ViewModel
}