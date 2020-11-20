package com.danielgimenez.myeconomy.domain.model

data class Expense(val id: Int, val amount: Float, val description: String, val type: Int, val date: String)