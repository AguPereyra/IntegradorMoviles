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

        private class AppDatabaseCallback : RoomDatabase.Callback() {
            override fun onOpen(db: SupportSQLiteDatabase) {
                super.onOpen(db)
                PopulateDbAsync(INSTANCE).execute()
            }
        }

        /*
        * Singleton way of getting Room DB*/
        fun getDatabase(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "app_db"
                ).fallbackToDestructiveMigration()
                .addCallback(AppDatabaseCallback())
                .build()
                INSTANCE = instance
                instance
            }
        }
    }

    /**
     * Populate the database in the background.
     */
    private class PopulateDbAsync internal constructor(db: AppDatabase?) :
        AsyncTask<Void?, Void?, Void?>() {
        private lateinit var mCattleDao: CattleDAO

        init {
            if (db != null) {
                mCattleDao = db.cattleDao()
            }
        }

        override fun doInBackground(vararg p0: Void?): Void? {
            // Start the app with a clean database every time.
            // Not needed if you only populate the database
            // when it is first created
            mCattleDao.deleteAll()
            val populationList = StaticDataGenerator.generateCattleList()
            for (cattle in populationList) {
                mCattleDao.insert(cattle)
            }
            return null
        }
    }


}