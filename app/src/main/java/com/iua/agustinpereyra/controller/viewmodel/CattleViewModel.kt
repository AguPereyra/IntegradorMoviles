package com.iua.agustinpereyra.controller.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.iua.agustinpereyra.repository.CattleRepository
import com.iua.agustinpereyra.repository.MonitoredCattleRepository
import com.iua.agustinpereyra.repository.database.entities.Cattle
import kotlinx.coroutines.launch
import java.lang.Error


class CattleViewModel(application: Application) : AndroidViewModel(application), BaseCattleViewModel {
    private val cattleRepository: CattleRepository = CattleRepository(application)
    private val monitoredCattleRepository: MonitoredCattleRepository = MonitoredCattleRepository(application)

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

    /**
     * addToMonitored adds the list of cattle as favorites to
     * the corresponding user
     */
    fun addToMonitored(userId: Int, cattleCaravans: List<String>) {
        viewModelScope.launch {
            monitoredCattleRepository.addMonitoredCattle(userId, cattleCaravans)
        }
    }

}