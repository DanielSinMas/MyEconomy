package com.danielgimenez.myeconomy.domain.model

import android.os.Parcelable
import com.danielgimenez.myeconomy.data.entity.ExpenseEntity
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Expense(val amount: Float, val description: String, val type: Int, val date: String): Parcelable{
    fun toEntity(): ExpenseEntity{
        var entity = ExpenseEntity(amount, description, type, date)
        return entity
    }
}