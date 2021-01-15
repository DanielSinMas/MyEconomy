package com.danielgimenez.myeconomy.data.repository

import android.content.Context
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.source.database.IDiskDataSource
import com.danielgimenez.myeconomy.data.source.network.INetworkDataSource
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.domain.usecase.expenses.GetExpensesByMonthRequest
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseRequest
import java.time.LocalDate
import java.util.*

class ExpenseRepository(private val context: Context,
                        private val diskDataSource: IDiskDataSource,
                        private val networkDataSource: INetworkDataSource) {

    fun insertExpense(request: InsertExpenseRequest): Response.Success<Expense> {
        diskDataSource.insertExpense(request.expense.toEntity())
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
}