package com.danielgimenez.myeconomy.data.repository

import android.content.Context
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.domain.model.Expense
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

open class BaseRepository {
    private val EXPENSES_COLLECTION = "expenses"

    fun saveExpense(context: Context, expense: Expense){
        val sharedPref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
        val user= sharedPref.getString("user", null)
        if(user != null){
            val expenseMap = expense.toMap(user)
            val db = Firebase.firestore
            val collection = db.collection(EXPENSES_COLLECTION)
                    .document(user)
                    .set(expenseMap)
        }
    }
}