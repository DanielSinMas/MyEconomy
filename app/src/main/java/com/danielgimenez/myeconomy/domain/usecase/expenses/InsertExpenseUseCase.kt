package com.danielgimenez.myeconomy.domain.usecase.expenses

import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.repository.ExpenseRepository
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.domain.usecase.BaseUseCase

open class InsertExpenseUseCase(val repository: ExpenseRepository): BaseUseCase<InsertExpenseRequest, Expense>(){
    override suspend fun run(): Response<Expense> {
        return repository.saveExpense(request!!)
    }
}