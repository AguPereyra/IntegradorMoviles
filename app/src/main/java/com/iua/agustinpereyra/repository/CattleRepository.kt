package com.iua.agustinpereyra.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.iua.agustinpereyra.repository.database.AppDatabase
import com.iua.agustinpereyra.repository.database.dao.CattleDAO
import com.iua.agustinpereyra.repository.database.entities.Cattle
import com.iua.agustinpereyra.repository.networking.ApiConnection
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

class CattleRepository(private val application: Application) {

    private var cattleDao: CattleDAO
    val allCattle: LiveData<List<Cattle>> = liveData {
        // First get DB data
        var list = getFromDB()
        emit(list)
        // Then search on the API
        list = getFromNetwork()
        emit(list)
        // Finally update the DB
        emit(updateDB(list))
    }

    init {
        val db = AppDatabase.getDatabase(application)
        cattleDao = db.cattleDao()
    }

    /**
     * getFromNetwork gets data from Network and returns the cattle list
     * Runs on IO threads.
     */
    private suspend fun getFromNetwork(): List<Cattle> = withContext(Dispatchers.IO) {
        // Delete data from DB
        //TODO: Not working because of way of implementing, follow
        cattleDao.deleteAll()
        // Get data from Web
        val cattleList = ApiConnection.getCattleList()
        cattleList
    }

    /**
     * getFromDB gets the cattle list that is at the DB
     */
    private suspend fun getFromDB(): List<Cattle> = withContext(Dispatchers.IO) {
        cattleDao.getAll()
    }

    /**
     * updateDB updates DB with data from API
     */
    private suspend fun updateDB(cattleList: List<Cattle>): List<Cattle> = withContext(Dispatchers.IO) {
        cattleDao.deleteAll()
        for (cattle in cattleList) {
            cattleDao.insert(cattle)
        }
        cattleDao.getAll()
    }
}