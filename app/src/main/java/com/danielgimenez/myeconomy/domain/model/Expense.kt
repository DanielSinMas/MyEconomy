package com.danielgimenez.myeconomy.domain.model

import android.os.Parcelable
import com.danielgimenez.myeconomy.data.entity.ExpenseEntity
import com.danielgimenez.myeconomy.data.entity.converter.LocalDateConverter
import kotlinx.android.parcel.Parcelize
import java.time.LocalDate

@Parcelize
data class Expense(val amount: Float, val description: String, val type: Int, val date: LocalDate): Parcelable{
    fun toEntity(): ExpenseEntity{
        var entity = ExpenseEntity(amount, description, type)
        entity.date = date
        return entity
    }
}