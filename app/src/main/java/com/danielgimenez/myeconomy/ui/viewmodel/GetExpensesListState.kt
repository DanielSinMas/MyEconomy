package com.danielgimenez.myeconomy.ui.viewmodel

import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.domain.model.Expense

sealed class GetExpenseListState{
    abstract val response: Response<List<Expense>>?
}

data class SuccessGetEntryListState(override val response: Response<List<Expense>>): GetExpenseListState()
data class LoadingGetEntryListState(override val response: Response<List<Expense>>? = null): GetExpenseListState()
data class ErrorGetEntryListState(override val response: Response<List<Expense>>): GetExpenseListState()