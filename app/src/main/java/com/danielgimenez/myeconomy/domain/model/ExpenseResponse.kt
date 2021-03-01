package com.danielgimenez.myeconomy.domain.model

import java.time.LocalDate

class ExpenseResponse(val amount: Float, val description: String, val type: Int, val date: String) {
}