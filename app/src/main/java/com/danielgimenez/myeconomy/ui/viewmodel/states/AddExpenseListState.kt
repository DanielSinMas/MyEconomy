package com.danielgimenez.myeconomy.ui.viewmodel

import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseResponse

sealed class AddExpenseListState{
    abstract val response: Response<InsertExpenseResponse>?
}

data class SuccessAddEntryListState(override val response: Response<InsertExpenseResponse>): AddExpenseListState()
data class LoadingAddEntryListState(override val response: Response<InsertExpenseResponse>? = null): AddExpenseListState()
data class ErrorAddEntryListState(override val response: Response<InsertExpenseResponse>): AddExpenseListState()