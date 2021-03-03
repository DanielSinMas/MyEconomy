package com.danielgimenez.myeconomy.domain.usecase.expenses

import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.repository.ExpenseRepository
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.domain.usecase.BaseUseCase
import com.google.firebase.ktx.Firebase

open class InsertExpenseUseCase(val repository: ExpenseRepository): BaseUseCase<InsertExpenseRequest, List<Expense>>(){
    override suspend fun run(): Response<List<Expense>> {
        return repository.insertExpense(request!!)
    }
}