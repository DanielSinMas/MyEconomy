package com.danielgimenez.myeconomy.domain.usecase.expenses

import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.repository.ExpenseRepository
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.domain.usecase.BaseUseCase

class GetExpensesUseCase(val repository: ExpenseRepository): BaseUseCase<Nothing, List<Expense>>(){
    override suspend fun run(): Response<List<Expense>> {
        return repository.getExpenses()
    }
}