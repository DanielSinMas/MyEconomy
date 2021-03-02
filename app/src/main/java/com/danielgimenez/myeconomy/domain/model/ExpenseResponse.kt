package com.danielgimenez.myeconomy.domain.model

import com.danielgimenez.myeconomy.utils.DateFunctions
import java.time.LocalDate

class ExpenseResponse(val amount: Float, val description: String, val type: Int, val date: String) {

    fun toExpense() = Expense(amount, description, type, DateFunctions.parseDateFromFirestore(date)!!)
}