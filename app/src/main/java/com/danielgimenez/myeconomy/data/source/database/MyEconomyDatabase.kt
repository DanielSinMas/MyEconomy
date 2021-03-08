package com.danielgimenez.myeconomy.data.source.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.danielgimenez.myeconomy.data.entity.ExpenseEntity
import com.danielgimenez.myeconomy.data.entity.TypeEntity
import com.danielgimenez.myeconomy.data.entity.converter.LocalDateConverter
import com.danielgimenez.myeconomy.data.source.database.dao.ExpenseDao
import com.danielgimenez.myeconomy.data.source.database.dao.TypeDao


@Database(entities = [ExpenseEntity::class, TypeEntity::class], version = 2)
@TypeConverters(LocalDateConverter::class)
abstract class MyEconomyDatabase: RoomDatabase(){
    abstract fun expenseDao(): ExpenseDao
    abstract fun typeDao(): TypeDao

    companion object{
        private const val DATABASE_NAME = "myeconomy_db"
        @Volatile
        private var INSTANCE: MyEconomyDatabase? = null

        val MIGRATION_1_2: Migration = object : Migration(1, 2) {
            override fun migrate(database: SupportSQLiteDatabase) {
                // Since we didn't alter the table, there's nothing else to do here.
                database.execSQL("ALTER TABLE EXPENSE_TYPES ADD COLUMN localid INTEGER NOT NULL DEFAULT 0")
            }
        }

        fun getInstace(context: Context): MyEconomyDatabase?{
            INSTANCE ?: synchronized(this){
                val rdc: Callback = object : Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        //db.execSQL("INSERT INTO EXPENSE_TYPES (name) VALUES ('Facturas')")
                        //db.execSQL("INSERT INTO EXPENSE_TYPES (name) VALUES ('Comida')")
                        //db.execSQL("INSERT INTO EXPENSE_TYPES (name) VALUES ('Entretenimiento')")
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
                        .addMigrations(MIGRATION_1_2)
                    .build()
            }
            return INSTANCE
        }

        fun dropDatabase(){
            INSTANCE?.clearAllTables()
        }
    }
}