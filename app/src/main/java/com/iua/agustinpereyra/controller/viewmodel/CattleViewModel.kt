package com.iua.agustinpereyra.controller.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.iua.agustinpereyra.repository.CattleRepository
import com.iua.agustinpereyra.repository.database.entities.Cattle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class CattleViewModel(application: Application) : AndroidViewModel(application) {
    private val cattleRepository: CattleRepository = CattleRepository(application)

    // Get asynchronously
    val cattleList: LiveData<List<Cattle>> = cattleRepository.allCattle

}