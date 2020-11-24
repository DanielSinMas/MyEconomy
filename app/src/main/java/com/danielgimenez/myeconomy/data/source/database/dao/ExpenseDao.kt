package com.danielgimenez.myeconomy.data.source.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.danielgimenez.myeconomy.data.entity.ExpenseEntity
import com.danielgimenez.myeconomy.data.entity.converter.LocalDateConverter
import java.time.LocalDate

@Dao
@TypeConverters(LocalDateConverter::class)
interface ExpenseDao{

    @Insert
    fun insert(expense: ExpenseEntity): Long

    @Update
    fun update(vararg expense: ExpenseEntity)

    @Delete
    fun delete(vararg expense: ExpenseEntity)

    @Query("SELECT * FROM " + ExpenseEntity.TABLE_NAME + " WHERE date between :from and :to")
    fun getExpensesByMonth(from: LocalDate, to: LocalDate): List<ExpenseEntity>

    @Query("SELECT * FROM " + ExpenseEntity.TABLE_NAME)
    fun getExpense(): List<ExpenseEntity>
}