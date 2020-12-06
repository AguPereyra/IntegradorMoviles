package com.iua.agustinpereyra.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.iua.agustinpereyra.controller.NetworkHelper
import com.iua.agustinpereyra.repository.database.AppDatabase
import com.iua.agustinpereyra.repository.database.dao.CattleDAO
import com.iua.agustinpereyra.repository.database.dao.UsersDAO
import com.iua.agustinpereyra.repository.database.entities.Cattle
import com.iua.agustinpereyra.repository.networking.ApiConnection
import kotlinx.coroutines.withContext
import kotlinx.coroutines.Dispatchers

class CattleRepository(private val application: Application) {

    private var cattleDao: CattleDAO
    val allCattle: LiveData<List<Cattle>>

    init {
        val db = AppDatabase.getDatabase(application)
        cattleDao = db.cattleDao()
        allCattle = cattleDao.getAll()
    }

    /**
     * getFromNetwork gets data from Network and returns the cattle list
     * Runs on IO threads.
     */
    private suspend fun getFromNetwork(): List<Cattle> = withContext(Dispatchers.IO) {
        // Delete data from DB
        cattleDao.deleteAll()
        // Get data from Web
        val cattleList = ApiConnection.getCattleList()
        cattleList
    }

    /**
     * updateCattleList updates DB with data from API. Doesn't return anything as we are
     * working with LiveData on DAO
     */
    private suspend fun updateDB(cattleList: List<Cattle>) = withContext(Dispatchers.IO) {
        cattleDao.deleteAll()
        for (cattle in cattleList) {
            cattleDao.insert(cattle)
        }
    }

    /**
     * refreshCattleList tries to get data from network and update DB
     */
    suspend fun refreshCattleList() = withContext(Dispatchers.IO) {
        if (NetworkHelper.isNetworkConnected(application.applicationContext)) {
            val list = getFromNetwork()
            updateDB(list)
        }
    }
}