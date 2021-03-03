package com.danielgimenez.myeconomy.domain.model

import com.danielgimenez.myeconomy.data.entity.ExpenseEntity
import com.danielgimenez.myeconomy.utils.DateFunctions

data class ExpenseRequest(val amount: Float, val description: String, val type: Int, val date: String){

    fun toEntity(): ExpenseEntity {
        var entity = ExpenseEntity(amount, description, type)
        entity.date = DateFunctions.parseDateFromFirestore(date)
        return entity
    }

    fun toExpense() = Expense(amount, description, type, DateFunctions.parseDateFromFirestore(date)!!)
}