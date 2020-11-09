package com.iua.agustinpereyra.repository

import androidx.lifecycle.LiveData
import com.iua.agustinpereyra.repository.database.AppDatabase
import com.iua.agustinpereyra.repository.database.dao.CattleDAO
import com.iua.agustinpereyra.repository.database.entities.Cattle

class CattleRepository(private val cattleDAO: CattleDAO) {

    private var cattleDao: CattleDAO
    private var allCattle: LiveData<List<Cattle>>

    init {
        val db = AppDatabase
    }

    suspend fun getAll(): LiveData<List<Cattle>> {
        return cattleDAO.getAll()
    }
}