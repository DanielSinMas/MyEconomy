package com.danielgimenez.myeconomy.ui.viewmodel

import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.domain.model.Type

sealed class InsertTypeState{
    abstract val response: Response<List<Type>>?
}

data class SuccessInsertTypetState(override val response: Response<List<Type>>): InsertTypeState()
data class LoadingInsertTypetState(override val response: Response<List<Type>>): InsertTypeState()
data class ErrorInsertTypeState(override val response: Response<List<Type>>): InsertTypeState()