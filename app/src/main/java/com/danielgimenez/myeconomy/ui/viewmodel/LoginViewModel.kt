package com.danielgimenez.myeconomy.ui.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.repository.ExpenseRepository
import com.danielgimenez.myeconomy.data.repository.LoginRepository
import com.danielgimenez.myeconomy.data.repository.TypeRepository
import com.danielgimenez.myeconomy.domain.model.Type
import com.danielgimenez.myeconomy.domain.usecase.login.GetDataForUserResponse
import com.danielgimenez.myeconomy.domain.usecase.types.InsertTypeRequest
import com.danielgimenez.myeconomy.ui.viewmodel.states.ErrorLoginState
import com.danielgimenez.myeconomy.ui.viewmodel.states.LoadingLogintState
import com.danielgimenez.myeconomy.ui.viewmodel.states.LoginState
import com.danielgimenez.myeconomy.ui.viewmodel.states.SuccessLogintState
import com.danielgimenez.myeconomy.utils.launchSilent
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
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
            loginLiveData.postValue(ErrorLoginState(Response.Error(Exception("Error retrieving user data"))))
        }
        saveDataLocally((data as Response.Success))
    }

    private fun saveDataLocally(data: Response.Success<GetDataForUserResponse>){
        val response = data.data
        var typesToInsert : ArrayList<Type>? = null
        if(response.types.size > 0) typesToInsert = response.types
        if(response.expenses.size > 0){
            var expensesResult = response.expenses.map { expense ->
                expenseRepository.saveExpenseLocally(listOf(expense.toExpense()))
            }
        }

        if(response.new_user){
            val db = Firebase.firestore
            val user = hashMapOf("email" to Firebase.auth.currentUser?.email!!)
            db.collection("users").document().set(user)
        }
        createTypes(typesToInsert, response)
    }

    private fun createTypes(types: ArrayList<Type>?, response: GetDataForUserResponse){
        var typesToInsert = ArrayList<Type>()
        if(types!=null){
            typesToInsert = types
        }
        else{
            val types = listOf(
                    Type(1, "Facturas", 1),
                    Type(2, "Comida", 2),
                    Type(3, "Entretenimiento", 3))

            types.map {
                typesToInsert.add(it)
            }
        }
        if(types!=null) saveTypesLocally(types, response)
        else insertTypesInFirestore(typesToInsert, response)
    }

    private fun insertTypesInFirestore(types: ArrayList<Type>, response: GetDataForUserResponse){
        val db = Firebase.firestore
        val collection = db.collection("types")
        val user = Firebase.auth.currentUser?.email!!
        db.runTransaction { transaction ->
            types.map { type ->
                collection.document().set(type.toMap(user))
            }
        }.addOnSuccessListener {
            launchSilent(coroutineContext, exceptionHandler, job) {
                saveTypesLocally(types, response)
            }
        }.addOnFailureListener{
            loginLiveData.postValue(ErrorLoginState(Response.Error(Exception("Error inserting types in Firestore"))))
        }
    }

    private fun saveTypesLocally(types: ArrayList<Type>, response: GetDataForUserResponse) = launchSilent(coroutineContext, exceptionHandler, job){
        types.map {
            typeRepository.insertType(InsertTypeRequest(it))
        }
        loginLiveData.postValue(SuccessLogintState(Response.Success(response)))
    }

    private val exceptionHandler = CoroutineExceptionHandler{_,_ ->
    }
}