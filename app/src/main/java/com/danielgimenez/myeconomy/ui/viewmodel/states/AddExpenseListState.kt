package com.danielgimenez.myeconomy.ui.viewmodel

import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.domain.model.Expense

sealed class AddExpenseListState{
    abstract val response: Response<List<Expense>>?
}

data class SuccessAddEntryListState(override val response: Response<List<Expense>>): AddExpenseListState()
data class LoadingAddEntryListState(override val response: Response<List<Expense>>? = null): AddExpenseListState()
data class ErrorAddEntryListState(override val response: Response<List<Expense>>): AddExpenseListState()