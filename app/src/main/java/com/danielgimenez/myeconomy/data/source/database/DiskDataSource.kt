package com.danielgimenez.myeconomy.data.source.database

import android.content.Context
import com.danielgimenez.myeconomy.data.entity.ExpenseEntity
import com.danielgimenez.myeconomy.data.entity.TypeEntity

open class DiskDataSource(context: Context): IDiskDataSource(){

    companion object{
        var database: MyEconomyDatabase? = null
    }

    init {
        database = MyEconomyDatabase.getInstace(context)
    }

    //EXPENSES
    override fun insertExpense(entity: ExpenseEntity): Long? { return database?.expenseDao()?.insert(entity)}

    //TYPES
    override fun insertType(entity: TypeEntity): Long? { return database?.typeDao()?.insert(entity)}
}