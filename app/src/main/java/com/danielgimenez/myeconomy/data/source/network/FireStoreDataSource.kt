package com.danielgimenez.myeconomy.data.source.network

import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.domain.model.ExpenseResponse
import com.danielgimenez.myeconomy.domain.model.Type
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseRequest
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseResponse
import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.util.concurrent.ExecutionException
import kotlin.math.exp

class FireStoreDataSource {

    private val TYPES_COLLECTION = "types"
    private val EXPENSES_COLLECTION = "expenses"
    private val USERS_COLLECTION = "users"

    fun getTypes(email: String): ArrayList<Type>{
        val db = Firebase.firestore
        val types = ArrayList<Type>()
        try {
            val task = Tasks.await(db.collection(TYPES_COLLECTION)
                    .whereEqualTo("user", email)
                    .get())

            if (!task.isEmpty) {
                for (document in task.documents) {
                    val localId = document.getLong(Type.localIdField)!!.toInt()
                    types.add(Type(localId, document.getString(Type.nameField)!!, localId))
                }
            }
        }
        catch (e: ExecutionException){
            throw e
        }
        return types
    }

    fun getExpenses(email: String): ArrayList<ExpenseResponse>{
        val db = Firebase.firestore
        val expenses = ArrayList<ExpenseResponse>()
        try{
            val task = Tasks.await(db.collection(EXPENSES_COLLECTION)
                    .whereEqualTo("user", email)
                    .get())
            if(!task.isEmpty){
                for(document in task.documents){
                    val amount = document.getDouble("amount")!!.toFloat()
                    val description = document.getString("description")!!
                    val type = document.getLong("type")!!.toInt()
                    val date = document.getString("date")!!
                    expenses.add(ExpenseResponse(amount, description, type, date))
                }
            }
        } catch (e: ExecutionException){
            throw e
        }
        return expenses
    }

    fun isNewUser(email: String): Boolean {
        val db = Firebase.firestore
        val new_user: Boolean
        try{
            val task = Tasks.await(db.collection(USERS_COLLECTION)
                    .whereEqualTo("email", email)
                    .get())
            new_user = task.isEmpty
        } catch (e: ExecutionException){
            throw e
        }
        return new_user
    }
}