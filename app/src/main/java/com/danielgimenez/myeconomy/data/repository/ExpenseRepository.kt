package com.danielgimenez.myeconomy.data.repository

import android.content.Context
import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.source.database.IDiskDataSource
import com.danielgimenez.myeconomy.data.source.network.INetworkDataSource
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseRequest

class ExpenseRepository(private val context: Context,
                        private val diskDataSource: IDiskDataSource,
                        private val networkDataSource: INetworkDataSource) {

    suspend fun insertExpense(request: InsertExpenseRequest): Response<List<Expense>>{
        diskDataSource.insertExpense(request.expense.toEntity())
        val list = ArrayList<Expense>()
        list.add(Expense(14.45f, "", 1, ""))
        return Response.Success(list)
    }
}