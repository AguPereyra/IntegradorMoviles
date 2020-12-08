package com.iua.agustinpereyra.controller.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.iua.agustinpereyra.repository.MonitoredCattleRepository
import com.iua.agustinpereyra.repository.database.entities.Cattle
import kotlinx.coroutines.launch


class MonitoredCattleViewModel(application: Application) : AndroidViewModel(application), BaseCattleViewModel {
    private val monitoredCattleRepository = MonitoredCattleRepository(application)

    // Get asynchronously
    override val cattleList: LiveData<List<Cattle>> = monitoredCattleRepository.monitoredCattle

    /**
     * setMonitoredCattleListOfUser sets the cattleList LiveData
     * with the monitored cattle list of the user whose ID is passed
     * by the parameters
     */
    fun setMonitoredCattleListOfUser(userId: Int) {
        viewModelScope.launch {
          monitoredCattleRepository.getAllCattleOfUser(userId)
        }
    }

    /**
     * updateCattleList updates the data of the monitored cattle list
     * for the passed user
     */
    fun updateCattleList(userId: Int) {
        viewModelScope.launch {
            monitoredCattleRepository.getAllCattleOfUser(userId)
        }
    }

}