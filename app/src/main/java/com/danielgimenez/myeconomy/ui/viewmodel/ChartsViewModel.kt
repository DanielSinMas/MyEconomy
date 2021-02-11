package com.danielgimenez.myeconomy.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danielgimenez.myeconomy.domain.usecase.charts.GetChartsRequest
import com.danielgimenez.myeconomy.domain.usecase.charts.GetChartsUseCase
import com.danielgimenez.myeconomy.utils.launchSilent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class ChartsViewModel @Inject constructor(private val getChartsUseCase: GetChartsUseCase,
                                          private val coroutineContext: CoroutineContext): ViewModel() {

    private val job: Job = Job()
    val getChartsLiveData = MutableLiveData<GetChartsListState>()

    fun getCharts(user: String, userId: String)= launchSilent(coroutineContext, exceptionHandler, job){
        val request = GetChartsRequest(user, userId)
        val result = getChartsUseCase.execute(request)
        getChartsLiveData.postValue(result as SuccessGetChartsListState)
    }

    private val exceptionHandler = CoroutineExceptionHandler{_,_ ->
    }
}