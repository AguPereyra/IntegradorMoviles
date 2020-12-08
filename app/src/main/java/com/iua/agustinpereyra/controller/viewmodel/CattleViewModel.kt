package com.iua.agustinpereyra.controller.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.iua.agustinpereyra.repository.CattleRepository
import com.iua.agustinpereyra.repository.database.entities.Cattle
import kotlinx.coroutines.launch


class CattleViewModel(application: Application) : AndroidViewModel(application), BaseCattleViewModel {
    private val cattleRepository: CattleRepository = CattleRepository(application)

    // Get asynchronously
    override val cattleList: LiveData<List<Cattle>> = cattleRepository.allCattle

    init {
        // Try to update from API
        viewModelScope.launch {
            cattleRepository.refreshCattleList()
        }
    }

    // Wrap the update method
    fun updateCattleList() {
        viewModelScope.launch {
          cattleRepository.refreshCattleList()
        }
    }

}