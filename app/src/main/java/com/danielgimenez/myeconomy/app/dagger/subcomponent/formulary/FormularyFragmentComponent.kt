package com.danielgimenez.myeconomy.app.dagger.subcomponent.formulary

import com.danielgimenez.myeconomy.ui.FormularyFragment
import dagger.Subcomponent

@Subcomponent(modules = [FormularyFragmentModule::class])
interface FormularyFragmentComponent {
    fun injectTo(fragment: FormularyFragment)
}