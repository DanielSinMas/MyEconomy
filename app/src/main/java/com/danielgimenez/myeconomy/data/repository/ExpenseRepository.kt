package com.danielgimenez.myeconomy.data.repository

import android.content.Context
import com.danielgimenez.myeconomy.R
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.source.database.IDiskDataSource
import com.danielgimenez.myeconomy.data.source.network.INetworkDataSource
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.domain.usecase.expenses.GetExpensesByDateRequest
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseRequest
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseResponse
import java.time.LocalDate

open class ExpenseRepository(private val context: Context,
                             private val diskDataSource: IDiskDataSource): BaseRepository() {

    fun saveExpenseLocally(list: List<Expense>): Response.Success<List<Expense>> {
        list.map { expense ->
            diskDataSource.insertExpense(expense.toEntity())
        }
        return Response.Success(list)
    }

    fun getExpenses(): Response.Success<List<Expense>> {
        val list = diskDataSource.getExpenses()!!
        return Response.Success(list)
    }

    fun getExpensesByMonth(month: GetExpensesByDateRequest): Response.Success<List<Expense>> {
        var initial = LocalDate.of(month.year!!, month.month, 1)
        val list = diskDataSource.getExpensesByMonth(initial.withDayOfMonth(1), initial.withDayOfMonth(initial.lengthOfMonth()))!!
        return Response.Success(list)
    }
}