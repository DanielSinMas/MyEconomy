package com.danielgimenez.myeconomy.ui.viewmodel

import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.domain.model.Expense

sealed class AddExpenseListState{
    abstract val response: Response<Expense>?
}

data class SuccessAddEntryListState(override val response: Response<Expense>): AddExpenseListState()
data class LoadingAddEntryListState(override val response: Response<Expense>? = null): AddExpenseListState()
data class ErrorAddEntryListState(override val response: Response<Expense>): AddExpenseListState()