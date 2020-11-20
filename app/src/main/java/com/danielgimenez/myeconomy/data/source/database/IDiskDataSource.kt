package com.danielgimenez.myeconomy.data.source.database

import com.danielgimenez.myeconomy.data.entity.ExpenseEntity

abstract class IDiskDataSource{

    abstract fun insertExpense(entity: ExpenseEntity): Long?
}