package com.iua.agustinpereyra.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.iua.agustinpereyra.repository.database.AppDatabase
import com.iua.agustinpereyra.repository.database.dao.CattleDAO
import com.iua.agustinpereyra.repository.database.entities.Cattle
import com.iua.agustinpereyra.repository.networking.ApiConnection
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

class CattleRepository(private val application: Application) {

    private var cattleDao: CattleDAO
    private lateinit var allCattle: List<Cattle>

    init {
        val db = AppDatabase.getDatabase(application)
        cattleDao = db.cattleDao()
    }

    /**
     * getAll tries to update the database data from Network if possible, and then
     * returns the cattle data from the Database, be it the updated data (if network connection
     * was possible), or the last data available in the DB.
     * Runs on IO threads.
     */
    //TODO: Change this to first respond with available data in DB and then ask for API
    suspend fun getAll(): List<Cattle> = withContext(Dispatchers.IO) {
        // Delete data from DB
        //TODO: Not working because of way of implementing, follow
        cattleDao.deleteAll()
        // Get data from Web
        val cattleList = ApiConnection.getCattleList()
        // Insert in DB
        for (cattle in cattleList) {
            cattleDao.insert(cattle)
        }
        allCattle = cattleDao.getAll()
        allCattle
    }
}