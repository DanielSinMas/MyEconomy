package com.danielgimenez.myeconomy.app.dagger.subcomponent.login

import com.danielgimenez.myeconomy.ui.activities.LoginActivity
import dagger.Subcomponent

@Subcomponent(modules = [LoginActivityModule::class])
interface LoginActivityComponent {
    fun injectInto(activity: LoginActivity)
}