package com.danielgimenez.myeconomy.domain.usecase.expenses

import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.domain.usecase.base.BaseRequest

class InsertExpenseRequest(var expense: Expense): BaseRequest{
    override fun validate(): Boolean {
        return true
    }
}