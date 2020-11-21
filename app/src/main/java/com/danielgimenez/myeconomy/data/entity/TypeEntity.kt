package com.danielgimenez.myeconomy.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import org.jetbrains.annotations.NotNull

@Entity(tableName = TypeEntity.TABLE_NAME)
data class TypeEntity(@ColumnInfo(name="name")@NotNull val name: String){

    companion object{
        const val TABLE_NAME = "EXPENSE_TYPES"
    }

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    var id : Int = 0
}