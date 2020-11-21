package com.danielgimenez.myeconomy.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.domain.model.Type
import com.danielgimenez.myeconomy.domain.usecase.expenses.GetExpensesUseCase
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseRequest
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseUseCase
import com.danielgimenez.myeconomy.domain.usecase.types.GetTypesUseCase
import com.danielgimenez.myeconomy.utils.launchSilent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FormularyViewModel @Inject constructor(private val insertExpenseUseCase: InsertExpenseUseCase,
                                             private val getExpensesUseCase: GetExpensesUseCase,
                                             private val getTypesUseCase: GetTypesUseCase,
                                             private val coroutineContext: CoroutineContext): ViewModel(){

    private val job: Job = Job()
    private lateinit var types: List<Type>

    var addExpenseListLiveData = MutableLiveData<AddExpenseListState>()
    var getExpenseListLiveData = MutableLiveData<GetExpenseListState>()

    fun insertExpense(expense: Expense) = launchSilent(coroutineContext, exceptionHandler, job){
        val request = InsertExpenseRequest(expense)
        val response = insertExpenseUseCase.execute(request)
        addExpenseListLiveData.postValue(SuccessAddEntryListState(response))
    }

    fun getExpenses() = launchSilent(coroutineContext, exceptionHandler, job){
        val response = getExpensesUseCase.execute()
        getExpenseListLiveData.postValue(SuccessGetEntryListState(response))
    }

    fun seachTypes(): Unit = launchSilent(coroutineContext, exceptionHandler, job){
        val response = getTypesUseCase.execute(null)
        types = (response as Response.Success).data
    }

    fun getTypes() = types.map { it.name }
    fun getTypeFromId(id: Int) = types.first{it.id == id}.name
    fun getId(type: String) = types.first { it.name == type }.id

    private val exceptionHandler = CoroutineExceptionHandler{_,_ ->
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }
}