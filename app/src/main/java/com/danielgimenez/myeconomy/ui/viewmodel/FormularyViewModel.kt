package com.danielgimenez.myeconomy.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FormularyViewModel @Inject constructor(private val coroutineContext: CoroutineContext): ViewModel(){

    private val job: Job = Job()

    var addExpenseListLiveData = MutableLiveData<AddExpenseListState>()

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}