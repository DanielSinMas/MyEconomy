package com.danielgimenez.myeconomy.data.source.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.danielgimenez.myeconomy.data.entity.ExpenseEntity
import com.danielgimenez.myeconomy.data.source.database.dao.ExpenseDao

@Database(entities = [ExpenseEntity::class], version = 1)
abstract class MyEconomyDatabase: RoomDatabase(){
    abstract fun expenseDao(): ExpenseDao

    companion object{
        private const val DATABASE_NAME = "myeconomy_db"
        @Volatile
        private var INSTANCE: MyEconomyDatabase? = null

        fun getInstace(context: Context): MyEconomyDatabase?{
            INSTANCE ?: synchronized(this){
                INSTANCE = Room.databaseBuilder(
                    context,
                    MyEconomyDatabase::class.java,
                    DATABASE_NAME
                ).build()
            }
            return INSTANCE
        }
    }
}