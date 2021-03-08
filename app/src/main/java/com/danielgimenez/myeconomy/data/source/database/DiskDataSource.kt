package com.danielgimenez.myeconomy.data.source.database

import android.content.Context
import androidx.lifecycle.LiveData
import com.danielgimenez.myeconomy.data.entity.ExpenseEntity
import com.danielgimenez.myeconomy.data.entity.TypeEntity
import com.danielgimenez.myeconomy.domain.model.Expense
import java.time.LocalDate

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
    override fun insertType(entity: TypeEntity): Long? {
        return database?.typeDao()?.insert(entity)
    }

    override fun getTypes(): List<TypeEntity>? { return database?.typeDao()?.getTypes()}

    override fun getExpensesByMonth(from: LocalDate, to: LocalDate): List<Expense>? { return database?.expenseDao()?.getExpensesByMonth(from, to)?.map { it.toModel() }}

    override fun getExpenses(): List<Expense>? { return database?.expenseDao()?.getExpense()?.map { it.toModel() }}
}