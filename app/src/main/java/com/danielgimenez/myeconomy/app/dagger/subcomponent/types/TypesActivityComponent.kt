package com.danielgimenez.myeconomy.app.dagger.subcomponent.types

import com.danielgimenez.myeconomy.ui.activities.TypesActivity
import dagger.Subcomponent

@Subcomponent(modules = [TypesActivityModule::class])
interface TypesActivityComponent {
    fun injectTo(activity: TypesActivity)
}