package com.danielgimenez.myeconomy.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.repository.ExpenseRepository
import com.danielgimenez.myeconomy.data.repository.LoginRepository
import com.danielgimenez.myeconomy.data.repository.TypeRepository
import com.danielgimenez.myeconomy.domain.model.Type
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseRequest
import com.danielgimenez.myeconomy.domain.usecase.login.GetDataForUserResponse
import com.danielgimenez.myeconomy.domain.usecase.types.InsertTypeRequest
import com.danielgimenez.myeconomy.ui.viewmodel.states.ErrorLoginState
import com.danielgimenez.myeconomy.ui.viewmodel.states.LoadingLogintState
import com.danielgimenez.myeconomy.ui.viewmodel.states.LoginState
import com.danielgimenez.myeconomy.ui.viewmodel.states.SuccessLogintState
import com.danielgimenez.myeconomy.utils.launchSilent
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LoginViewModel @Inject constructor(private val loginRepository: LoginRepository,
                                         private val typeRepository: TypeRepository,
                                         private val expenseRepository: ExpenseRepository,
                                         private val coroutineContext: CoroutineContext): ViewModel() {

    private val job = Job()

    val loginLiveData = MutableLiveData<LoginState>()

    fun getDataForUser(id_token: String) = launchSilent(coroutineContext, exceptionHandler, job){
        loginLiveData.postValue(LoadingLogintState(null))
        val data = loginRepository.getDataForUser(id_token)
        if(data is Response.Error){
            loginLiveData.postValue(ErrorLoginState(data))
        }
        saveDataLocally((data as Response.Success))
    }

    private fun saveDataLocally(data: Response.Success<GetDataForUserResponse>){
        val response = data.data
        if(response.types != null && response.types.size > 0){
            var typesResult = response.types.map { type ->
                typeRepository.insertType(InsertTypeRequest(type))
            }
        }
        else if(response.types.size == 0){
            typeRepository.insertType(InsertTypeRequest(Type(1, "Facturas")))
            typeRepository.insertType(InsertTypeRequest(Type(2, "Comida")))
            typeRepository.insertType(InsertTypeRequest(Type(3, "Entretenimiento")))
        }
        if(response.expenses != null && response.expenses.size > 0){
            var expensesResult = response.expenses.map { expense ->
                expenseRepository.saveExpenseLocally(listOf(expense.toExpense()))
            }
        }
        loginLiveData.postValue(SuccessLogintState(data))
    }

    private val exceptionHandler = CoroutineExceptionHandler{_,_ ->
    }
}