package com.danielgimenez.myeconomy.ui

import androidx.multidex.MultiDexApplication
import com.danielgimenez.myeconomy.app.dagger.ApplicationComponent
import com.danielgimenez.myeconomy.app.dagger.DaggerApplicationComponent
import com.danielgimenez.myeconomy.app.dagger.module.ApplicationModule
import com.jakewharton.threetenabp.AndroidThreeTen

open class App :  MultiDexApplication() {
    companion object {
        lateinit var graph: ApplicationComponent
    }

    override fun onCreate() {
        super.onCreate()
        initializeDagger()
        initTimezones()
    }

    private fun initializeDagger() {
        graph = DaggerApplicationComponent.builder()
            .applicationModule(ApplicationModule(this))
            .build()
    }

    private fun initTimezones() {
        AndroidThreeTen.init(this)
    }

    fun getApplicationComponent(): ApplicationComponent {
        return graph
    }
}

