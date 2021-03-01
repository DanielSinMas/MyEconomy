package com.danielgimenez.myeconomy.ui.viewmodel

import android.text.format.DateUtils
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.repository.ExpenseRepository
import com.danielgimenez.myeconomy.data.repository.FirestoreRepository
import com.danielgimenez.myeconomy.data.repository.TypeRepository
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.domain.model.Type
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseRequest
import com.danielgimenez.myeconomy.domain.usecase.types.InsertTypeRequest
import com.danielgimenez.myeconomy.domain.usecase.types.InsertTypeUseCase
import com.danielgimenez.myeconomy.utils.DateFunctions
import com.danielgimenez.myeconomy.utils.launchSilent
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.*
import java.time.LocalDate
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LoginViewModel @Inject constructor(val typeRepository: TypeRepository,
                                         val expenseRepository: ExpenseRepository,
                                        private val coroutineContext: CoroutineContext): ViewModel() {

    private val job = Job()

    var insertTypeLiveData = MutableLiveData<InsertTypeState>()
    var getExpensesLiveData = MutableLiveData<GetExpenseListState>()

    fun insertType(type: Type) = launchSilent(coroutineContext, exceptionHandler, job){
        val request = InsertTypeRequest(type)
        val result = typeRepository.insertType(request)
    }

    fun insertTypes(list: List<Type>) = launchSilent(coroutineContext, exceptionHandler, job){
        val result = list.map {
            async {
                val result = typeRepository.insertType(InsertTypeRequest(it))
                Log.e("Result", result.toString())
            }
        }.awaitAll()
        val typesList = typeRepository.getTypes()!!
        insertTypeLiveData.postValue(SuccessInsertTypetState(Response.Success(typesList)))
    }

    fun getDataFromFirestoreAndSaveLocally(user: String) = launchSilent(coroutineContext, exceptionHandler, job){
        val db = Firebase.firestore
        var collection = db.collection("expenses")
        collection.whereEqualTo("user", user).get().addOnCompleteListener{ task ->
            if(task.isSuccessful){
                val expensesList = task.result?.documents?.map { document ->
                    val date = DateFunctions.parseDateFromFirestore(document["date"] as String)
                    if(date == null){
                        getExpensesLiveData.postValue(ErrorGetEntryListState(Response.Error(Exception("Error con las fechas"))))
                    }
                    else{
                        Expense((document["amount"] as Double).toFloat(), document["description"] as String, (document["type"] as Long).toInt(), date)
                    }
                }
                collection = db.collection("types")
                collection.whereEqualTo("user", user).get().addOnCompleteListener{ task ->
                    if(task.isSuccessful){
                        val list = task.result?.documents?.map { document ->
                            Type((document["id"] as Long).toInt(), document["name"] as String)
                        }
                        if (list != null) {
                            insertTypesWithoutResult(list)
                        }
                        insertExpenses(expensesList as List<Expense>)
                    }
                }
            }
        }
    }

    private fun insertTypesWithoutResult(list: List<Type>) = launchSilent(coroutineContext, exceptionHandler, job){
        val result = list.map {
            async {
                val result = typeRepository.insertType(InsertTypeRequest(it))
                Log.e("Result", result.toString())
            }
        }.awaitAll()
    }

    fun insertExpenses(list: List<Expense>) = launchSilent(coroutineContext, exceptionHandler, job){
        val result = list.map { expense ->
            async {
                expenseRepository.insertExpense(InsertExpenseRequest(expense as Expense)).data
            }
        }.awaitAll()
        getExpensesLiveData.postValue(SuccessGetEntryListState(Response.Success(result)))
    }

    private val exceptionHandler = CoroutineExceptionHandler{_,_ ->
    }
}