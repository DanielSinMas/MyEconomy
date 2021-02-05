package com.danielgimenez.myeconomy.data.repository

import android.content.Context
import android.util.Log
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.source.database.IDiskDataSource
import com.danielgimenez.myeconomy.data.source.network.INetworkDataSource
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.domain.usecase.expenses.GetExpensesByMonthRequest
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseRequest
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.google.gson.Gson
import java.time.LocalDate

class ExpenseRepository(private val context: Context,
                        private val diskDataSource: IDiskDataSource,
                        private val networkDataSource: INetworkDataSource) {

    private val EXPENSES_COLLECTION = "expenses"

    fun saveExpense(request: InsertExpenseRequest): Response.Success<Expense> {
        val result = diskDataSource.insertExpense(request.expense.toEntity())
        saveExpense(request.expense)
        return Response.Success(request.expense)
    }

    fun getExpenses(): Response.Success<List<Expense>> {
        val list = diskDataSource.getExpenses()!!
        return Response.Success(list)
    }

    fun getExpensesByMonth(month: GetExpensesByMonthRequest): Response.Success<List<Expense>> {
        var initial = LocalDate.of(month.year!!, month.month, 1)
        val list = diskDataSource.getExpensesByMonth(initial.withDayOfMonth(1), initial.withDayOfMonth(initial.lengthOfMonth()))!!
        return Response.Success(list)
    }

    private fun saveExpense(expense: Expense){
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