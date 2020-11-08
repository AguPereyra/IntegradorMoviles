package com.iua.agustinpereyra.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.iua.agustinpereyra.repository.database.dao.UserDAO
import com.iua.agustinpereyra.repository.database.entities.User


@Database(entities = arrayOf(User::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userDao(): UserDAO

    companion object {

        @Volatile
        private var INSTANCE : AppDatabase? = null

        /*
        * Singleton way of getting Room DB*/
        fun getDatabase(context : Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }

}