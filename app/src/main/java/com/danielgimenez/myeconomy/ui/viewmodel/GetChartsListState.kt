package com.danielgimenez.myeconomy.ui.viewmodel

import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.domain.usecase.charts.GetChartsResponse

sealed class GetChartsListState{
    abstract val response: Response<GetChartsResponse>?
}

data class SuccessGetChartsListState(override val response: Response<GetChartsResponse>): GetChartsListState()
data class LoadingGetChartsListState(override val response: Response<GetChartsResponse>? = null): GetChartsListState()
data class ErrorGetChartsListState(override val response: Response<GetChartsResponse>): GetChartsListState()