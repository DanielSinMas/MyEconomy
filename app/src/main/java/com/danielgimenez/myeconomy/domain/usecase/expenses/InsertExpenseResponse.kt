package com.danielgimenez.myeconomy.domain.usecase.expenses

import com.danielgimenez.myeconomy.domain.model.ExpenseResponse

data class InsertExpenseResponse(var expenses: List<ExpenseResponse>)
