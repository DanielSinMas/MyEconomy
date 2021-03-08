package com.danielgimenez.myeconomy.domain.usecase.expenses

import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.repository.ExpenseRepository
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.domain.usecase.BaseUseCase
import com.google.firebase.ktx.Firebase

open class InsertExpenseUseCase(val repository: ExpenseRepository): BaseUseCase<InsertExpenseRequest, InsertExpenseResponse>(){
    override suspend fun run(): Response<InsertExpenseResponse> {
        return repository.insertExpense(request!!)
    }
}