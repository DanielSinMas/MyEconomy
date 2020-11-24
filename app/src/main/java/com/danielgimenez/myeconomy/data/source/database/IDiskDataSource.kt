package com.danielgimenez.myeconomy.data.source.database

import com.danielgimenez.myeconomy.data.entity.ExpenseEntity
import com.danielgimenez.myeconomy.data.entity.TypeEntity
import com.danielgimenez.myeconomy.domain.model.Expense
import java.time.LocalDate

abstract class IDiskDataSource{

    abstract fun insertExpense(entity: ExpenseEntity): Long?

    abstract fun insertType(entity: TypeEntity): Long?

    abstract fun getExpenses(): List<Expense>?

    abstract fun getExpensesByMonth(from: LocalDate, to: LocalDate): List<Expense>?

    abstract fun getTypes(): List<TypeEntity>?
}