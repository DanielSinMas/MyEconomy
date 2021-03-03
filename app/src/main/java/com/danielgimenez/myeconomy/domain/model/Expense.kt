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

    fun toMap(user: String) =
        hashMapOf(
            "amount" to amount,
            "description" to description,
            "type" to type,
            "date" to date.toString(),
            "user" to user
        )

    fun toRequest() = ExpenseRequest(amount, description, type, date.toString())
}