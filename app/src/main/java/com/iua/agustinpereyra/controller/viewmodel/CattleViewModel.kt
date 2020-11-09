package com.iua.agustinpereyra.controller.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.iua.agustinpereyra.repository.CattleRepository
import com.iua.agustinpereyra.repository.database.AppDatabase
import com.iua.agustinpereyra.repository.database.entities.Cattle

class CattleViewModel(application: Application) : AndroidViewModel(application) {
    private val cattleRepository: CattleRepository

    private var cattleList: LiveData<List<Cattle>>

    init {
        cattleRepository = CattleRepository(application)
        cattleList = cattleRepository.getAll()
    }

    fun getCattleList() : LiveData<List<Cattle>> {
        return cattleList
    }

}