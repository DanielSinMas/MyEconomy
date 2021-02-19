package com.danielgimenez.myeconomy.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.danielgimenez.myeconomy.data.source.database.MyEconomyDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivityViewModel: ViewModel() {

    fun dropDatabase(){
        viewModelScope.launch(Dispatchers.IO) {
            MyEconomyDatabase.dropDatabase()
        }
    }
}