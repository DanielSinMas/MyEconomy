package com.danielgimenez.myeconomy.data.source.database.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.danielgimenez.myeconomy.data.entity.TypeEntity

@Dao
interface TypeDao {

    @Insert
    fun insert(type: TypeEntity): Long?

    @Update
    fun update(vararg expense: TypeEntity)

    @Delete
    fun delete(vararg expense: TypeEntity)

    @Query("SELECT * FROM " + TypeEntity.TABLE_NAME)
    fun getExpense(): LiveData<List<TypeEntity>>
}