package com.danielgimenez.myeconomy.domain.usecase.expenses

import com.danielgimenez.myeconomy.domain.model.Expense
import com.danielgimenez.myeconomy.domain.model.ExpenseRequest
import com.danielgimenez.myeconomy.domain.usecase.base.BaseRequest
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

class InsertExpenseRequest(
        var expenses: List<ExpenseRequest>): BaseRequest{
    override fun validate(): Boolean {
        return true
    }
}