package com.iua.agustinpereyra.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.iua.agustinpereyra.controller.NetworkHelper
import com.iua.agustinpereyra.repository.database.AppDatabase
import com.iua.agustinpereyra.repository.database.dao.CattleDAO
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
        // Get data from Web
        val cattleList = ApiConnection.getCattleList()
        cattleList
    }

    /**
     * updateDB updates DB with data from API. It inserts new bovines if there are
     * ones, updates the ones we have, and deletes the ones that were in the database
     * but not in the passed array. Doesn't return anything as we are
     * working with LiveData on DAO
     */
    private suspend fun updateDB(cattleList: List<Cattle>) = withContext(Dispatchers.IO) {
        // TODO: Check for optimization
        // Get the current list of cattle
        val currentCattleList = allCattle.value as MutableList<Cattle>?

        // Get the received cattle list as a mutable list, to only leave the new bovines if any
        val newCattleList : MutableList<Cattle> = ArrayList(cattleList)

        if (currentCattleList != null && currentCattleList.size > 0) {
            // Check and separate data to update, delete and insert
            val updateCattle = mutableListOf<Cattle>()
            for (receivedCattle in cattleList) {
                for (currentCattle in currentCattleList) {
                    if (receivedCattle.caravan == currentCattle.caravan) {
                        // Set to update and remove from current list and received list
                        // Those who remain in current list must be deleted
                        // Those who remain in received list must be inserted
                        updateCattle.add(receivedCattle)
                        currentCattleList.remove(currentCattle)
                        newCattleList.remove(currentCattle)
                        break
                    }
                }
            }

            // Delete corresponding cattle
            for (cattle in currentCattleList) {
                cattleDao.delete(cattle.caravan)
            }
        }
        // Insert the remaining cattle
        for (cattle in newCattleList) {
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