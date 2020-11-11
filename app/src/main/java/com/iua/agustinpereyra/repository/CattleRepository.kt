package com.iua.agustinpereyra.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.iua.agustinpereyra.repository.database.AppDatabase
import com.iua.agustinpereyra.repository.database.dao.CattleDAO
import com.iua.agustinpereyra.repository.database.entities.Cattle
import com.iua.agustinpereyra.repository.networking.ApiConnection

class CattleRepository(private val application: Application) {

    private var cattleDao: CattleDAO
    private lateinit var allCattle: LiveData<List<Cattle>>

    init {
        val db = AppDatabase.getDatabase(application)
        cattleDao = db.cattleDao()
    }

    fun getAll(): LiveData<List<Cattle>> {
        // Delete data from DB
        //TODO: Not working because of way of implementing, follow https://codelabs.developers.google.com/codelabs/kotlin-android-training-coroutines-and-room/#5
        cattleDao.deleteAll()
        // Get data from Web
        val cattleList = ApiConnection.getCattleList()
        // Insert in DB
        for (cattle in cattleList) {
            cattleDao.insert(cattle)
        }
        allCattle = cattleDao.getAll()
        return allCattle
    }
}