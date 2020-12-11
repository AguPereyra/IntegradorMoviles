package com.iua.agustinpereyra.repository.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.iua.agustinpereyra.repository.database.dao.CattleDAO
import com.iua.agustinpereyra.repository.database.dao.MonitoredCattleDAO
import com.iua.agustinpereyra.repository.database.dao.UsersDAO
import com.iua.agustinpereyra.repository.database.entities.Cattle
import com.iua.agustinpereyra.repository.database.entities.MonitoredCattle
import com.iua.agustinpereyra.repository.database.entities.Users


@Database(entities = [Cattle::class, Users::class, MonitoredCattle::class], version = 10)
abstract class AppDatabase : RoomDatabase() {

    abstract fun cattleDao(): CattleDAO
    abstract fun usersDao(): UsersDAO
    abstract fun monitoredCattleDao(): MonitoredCattleDAO

    companion object {

        @Volatile
        private var INSTANCE : AppDatabase? = null

        /*
        * Singleton way of getting Room DB
        * */
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