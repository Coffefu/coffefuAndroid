package com.example.coffefu.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.coffefu.dao.OrderDao
import com.example.coffefu.entities.ProductPosition

@Database(entities = [ProductPosition::class], version = 1)
abstract class OrderDatabase : RoomDatabase() {

    abstract fun orderDao(): OrderDao

    companion object {
        var INSTANCE: OrderDatabase? = null

        fun getAppDataBase(context: Context): OrderDatabase? {
            if (INSTANCE == null) {
                synchronized(OrderDatabase::class) {
                    INSTANCE = Room.databaseBuilder(
                        context.applicationContext,
                        OrderDatabase::class.java,
                        "note_db"
                    ).build()
                }
            }
            return INSTANCE
        }

        fun destroyDataBase() {
            INSTANCE = null
        }
    }
}