package com.danielgimenez.myeconomy.ui.viewmodel.states

import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.domain.model.Type
import com.danielgimenez.myeconomy.domain.usecase.expenses.InsertExpenseResponse

sealed class GetTypesState{
    abstract val response: Response<List<Type>>?
}

data class SuccessGetTypesState(override val response: Response<List<Type>>): GetTypesState()
data class LoadingGetTypesState(override val response: Response<List<Type>>? = null): GetTypesState()
data class ErrorGetTypesState(override val response: Response<List<Type>>): GetTypesState()