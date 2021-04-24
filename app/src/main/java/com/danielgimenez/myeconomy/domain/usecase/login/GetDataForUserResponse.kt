package com.danielgimenez.myeconomy.domain.usecase.login

import com.danielgimenez.myeconomy.domain.model.ExpenseResponse
import com.danielgimenez.myeconomy.domain.model.Type

data class GetDataForUserResponse(var new_user: Boolean, var types: ArrayList<Type>, var expenses: ArrayList<ExpenseResponse>) {
}