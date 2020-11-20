package com.danielgimenez.myeconomy.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = ExpenseEntity.TABLE_NAME)
data class ExpenseEntity(
    @ColumnInfo(name="amount") @NotNull var amount: Float,
    @ColumnInfo(name="description") var description: String,
    @ColumnInfo(name="type") @NotNull var type: Int,
    @ColumnInfo(name="date") @NotNull var date: String){

    companion object{
        const val TABLE_NAME = "EXPENSES"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id : Int = 0
}