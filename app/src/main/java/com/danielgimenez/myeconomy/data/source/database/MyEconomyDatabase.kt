package com.danielgimenez.myeconomy.data.source.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.danielgimenez.myeconomy.data.entity.ExpenseEntity
import com.danielgimenez.myeconomy.data.entity.TypeEntity
import com.danielgimenez.myeconomy.data.entity.converter.LocalDateConverter
import com.danielgimenez.myeconomy.data.source.database.dao.ExpenseDao
import com.danielgimenez.myeconomy.data.source.database.dao.TypeDao


@Database(entities = [ExpenseEntity::class, TypeEntity::class], version = 1)
@TypeConverters(LocalDateConverter::class)
abstract class MyEconomyDatabase: RoomDatabase(){
    abstract fun expenseDao(): ExpenseDao
    abstract fun typeDao(): TypeDao

    companion object{
        private const val DATABASE_NAME = "myeconomy_db"
        @Volatile
        private var INSTANCE: MyEconomyDatabase? = null

        fun getInstace(context: Context): MyEconomyDatabase?{
            INSTANCE ?: synchronized(this){
                val rdc: Callback = object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        db.execSQL("INSERT INTO EXPENSE_TYPES (name) VALUES ('Facturas')")
                        db.execSQL("INSERT INTO EXPENSE_TYPES (name) VALUES ('Comida')")
                        db.execSQL("INSERT INTO EXPENSE_TYPES (name) VALUES ('Entretenimiento')")
                    }

                    override fun onOpen(db: SupportSQLiteDatabase) {
                        // do something every time database is open
                    }
                }

                INSTANCE = Room.databaseBuilder(
                    context,
                    MyEconomyDatabase::class.java,
                    DATABASE_NAME
                ).addCallback(rdc)
                    .build()
            }
            return INSTANCE
        }
    }
}