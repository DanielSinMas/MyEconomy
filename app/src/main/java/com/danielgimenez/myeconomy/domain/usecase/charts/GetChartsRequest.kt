package com.danielgimenez.myeconomy.domain.usecase.charts

import com.danielgimenez.myeconomy.domain.usecase.base.BaseRequest

class GetChartsRequest(val user: String, val userId: String): BaseRequest {
    override fun validate(): Boolean {
        return true
    }
}