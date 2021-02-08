package com.danielgimenez.myeconomy.app.dagger.subcomponent.formulary.charts

import com.danielgimenez.myeconomy.ui.activities.ChartsFragment
import dagger.Subcomponent

@Subcomponent(modules = [ChartsFragmentModule::class])
interface ChartsFragmentComponent {
    fun injectTo(fragment: ChartsFragment)
}