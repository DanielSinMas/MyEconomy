package com.danielgimenez.myeconomy.data.source.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.danielgimenez.myeconomy.data.entity.ExpenseEntity

@Dao
interface ExpenseDao{

    @Insert
    fun insert(expense: ExpenseEntity): Long

    @Update
    fun update(vararg expense: ExpenseEntity)

    @Delete
    fun delete(vararg expense: ExpenseEntity)

    @Query("SELECT * FROM " + ExpenseEntity.TABLE_NAME)
    fun getExpense(): LiveData<List<ExpenseEntity>>
}