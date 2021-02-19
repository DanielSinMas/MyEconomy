package com.danielgimenez.myeconomy.domain.usecase.expenses

import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.repository.ExpenseRepository
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.domain.usecase.BaseUseCase

class GetExpenseByDateUseCase(val repository: ExpenseRepository): BaseUseCase<GetExpensesByDateRequest, List<Expense>>() {
    override suspend fun run(): Response<List<Expense>> {
        return repository.getExpensesByMonth(request!!)
    }
}