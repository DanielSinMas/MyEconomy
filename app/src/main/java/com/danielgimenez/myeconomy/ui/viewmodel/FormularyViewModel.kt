package com.danielgimenez.myeconomy.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseRequest
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseUseCase
import com.danielgimenez.myeconomy.utils.launchSilent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FormularyViewModel @Inject constructor(private val insertExpenseUseCase: InsertExpenseUseCase,
                                             private val coroutineContext: CoroutineContext): ViewModel(){

    private val job: Job = Job()

    var addExpenseListLiveData = MutableLiveData<AddExpenseListState>()

    fun insertExpense(expense: Expense) = launchSilent(coroutineContext, exceptionHandler, job){
        val request = InsertExpenseRequest(expense)
        val response = insertExpenseUseCase.execute(request)
        addExpenseListLiveData.postValue(SuccessAddEntryListState(response))
    }

    private val exceptionHandler = CoroutineExceptionHandler{_,_ ->
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}