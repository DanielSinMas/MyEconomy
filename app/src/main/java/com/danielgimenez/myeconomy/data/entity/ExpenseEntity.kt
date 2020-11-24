package com.danielgimenez.myeconomy.data.entity

import androidx.room.*
import com.danielgimenez.myeconomy.data.entity.converter.LocalDateConverter
import com.danielgimenez.myeconomy.domain.model.Expense
import org.jetbrains.annotations.NotNull
import java.time.LocalDate

@Entity(tableName = ExpenseEntity.TABLE_NAME)
data class ExpenseEntity(
    @ColumnInfo(name="amount") @NotNull var amount: Float,
    @ColumnInfo(name="description") var description: String,
    @ColumnInfo(name="type") @NotNull var type: Int){

    @TypeConverters(LocalDateConverter::class)
    var date: LocalDate? = null

    companion object{
        const val TABLE_NAME = "EXPENSES"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id : Int = 0

    fun toModel(): Expense{
        return Expense(amount, description, type, date!!)
    }
}