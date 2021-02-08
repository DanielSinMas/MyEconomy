package com.danielgimenez.myeconomy.domain.usecase.charts

import com.danielgimenez.myeconomy.Response
import com.danielgimenez.myeconomy.data.repository.ChartsRepository
import com.danielgimenez.myeconomy.domain.usecase.BaseUseCase

class GetChartsUseCase(val repository: ChartsRepository): BaseUseCase<GetChartsRequest, GetChartsResponse>() {
    override suspend fun run(): Response<GetChartsResponse> {
        return repository.getCharts(request!!)
    }

}