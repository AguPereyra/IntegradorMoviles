package com.iua.agustinpereyra.repository.database

import android.content.Context
import android.os.AsyncTask
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.iua.agustinpereyra.controller.StaticDataGenerator
import com.iua.agustinpereyra.repository.database.dao.CattleDAO
import com.iua.agustinpereyra.repository.database.entities.Cattle


@Database(entities = arrayOf(Cattle::class), version = 3)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cattleDao(): CattleDAO

    companion object {

        @Volatile
        private var INSTANCE : AppDatabase? = null

        /*
        * Singleton way of getting Room DB*/
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                ).fallbackToDestructiveMigration()
                .build()
                INSTANCE = instance
                instance
            }
        }
    }

}