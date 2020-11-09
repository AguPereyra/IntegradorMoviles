package com.iua.agustinpereyra.repository

import android.app.Application
import androidx.lifecycle.LiveData
import com.iua.agustinpereyra.repository.database.AppDatabase
import com.iua.agustinpereyra.repository.database.dao.CattleDAO
import com.iua.agustinpereyra.repository.database.entities.Cattle

class CattleRepository(private val application: Application) {

    private var cattleDao: CattleDAO
    private var allCattle: LiveData<List<Cattle>>

    init {
        val db = AppDatabase.getDatabase(application)
        cattleDao = db.cattleDao()
        allCattle = cattleDao.getAll()
    }

    fun getAll(): LiveData<List<Cattle>> {
        //TODO: Remove, just for testing
        return allCattle
    }
}