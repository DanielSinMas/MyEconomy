package com.danielgimenez.myeconomy.data.source.database

import android.content.Context
import com.danielgimenez.myeconomy.data.entity.ExpenseEntity

open class DiskDataSource(context: Context): IDiskDataSource(){

    companion object{
        var database: MyEconomyDatabase? = null
    }

    init {
        database = MyEconomyDatabase.getInstace(context)
    }

    override fun insertExpense(entity: ExpenseEntity): Long? { return database?.expenseDao()?.insert(entity)}
}