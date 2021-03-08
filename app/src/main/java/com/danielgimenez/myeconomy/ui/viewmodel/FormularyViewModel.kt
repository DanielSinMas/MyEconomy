package com.danielgimenez.myeconomy.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.domain.model.Type
import com.danielgimenez.myeconomy.domain.usecase.expenses.*
import com.danielgimenez.myeconomy.domain.usecase.types.GetTypesUseCase
import com.danielgimenez.myeconomy.utils.launchSilent
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class FormularyViewModel @Inject constructor(private val insertExpenseUseCase: InsertExpenseUseCase,
                                             private val getExpensesUseCase: GetExpensesUseCase,
                                             private val getExpenseByMonthUseCase: GetExpenseByDateUseCase,
                                             private val getTypesUseCase: GetTypesUseCase,
                                             private val coroutineContext: CoroutineContext): ViewModel(){

    private val job: Job = Job()

    var addExpenseListLiveData = MutableLiveData<AddExpenseListState>()
    var getExpenseListLiveData = MutableLiveData<GetExpenseListState>()

    fun insertExpense(expense: Expense) = launchSilent(coroutineContext, exceptionHandler, job){
        val request = InsertExpenseRequest(listOf(expense.toRequest()))
        val response = insertExpenseUseCase.execute(request)
        if(response is Response.Success) {
            addExpenseListLiveData.postValue(SuccessAddEntryListState(response))
        }
        else if(response is Response.Error){
            addExpenseListLiveData.postValue(ErrorAddEntryListState(response))
        }
    }

    fun getExpenses() = launchSilent(coroutineContext, exceptionHandler, job){
        val response = getExpensesUseCase.execute()
        getExpenseListLiveData.postValue(SuccessGetEntryListState(response))
    }

    fun getExpensesByDate(month: Int, year: Int) = launchSilent(coroutineContext, exceptionHandler, job){
        val request = GetExpensesByDateRequest(month, year)
        val response = getExpenseByMonthUseCase.execute(request)
        getExpenseListLiveData.postValue(SuccessGetEntryListState(response))
    }

    fun searchTypes(): Unit = launchSilent(coroutineContext, exceptionHandler, job){
        val response = getTypesUseCase.execute(null)
        types = (response as Response.Success).data
    }

    fun getTypes() = types
    fun getTypeFromId(id: Int) = types.first{it.localId == id}.name
    fun getId(type: String) = types.first { it.name == type }.localId

    private val exceptionHandler = CoroutineExceptionHandler{_,_ ->
    }

    override fun onCleared() {
        super.onCleared()
        job.cancel()
    }

    companion object{
        lateinit var types: List<Type>
    }
}