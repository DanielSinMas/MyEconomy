package com.danielgimenez.myeconomy.data.source.database

import com.danielgimenez.myeconomy.data.entity.ExpenseEntity
import com.danielgimenez.myeconomy.data.entity.TypeEntity

abstract class IDiskDataSource{

    abstract fun insertExpense(entity: ExpenseEntity): Long?

    abstract fun insertType(entity: TypeEntity): Long?
}