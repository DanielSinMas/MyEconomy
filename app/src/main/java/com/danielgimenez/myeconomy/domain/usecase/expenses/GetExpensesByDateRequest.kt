package com.danielgimenez.myeconomy.domain.usecase.expenses

import com.danielgimenez.myeconomy.domain.usecase.base.BaseRequest
import java.util.*

class GetExpensesByDateRequest(var month: Int, var year: Int? = Calendar.getInstance()[Calendar.YEAR]): BaseRequest {
    override fun validate(): Boolean {
        return true
    }
}