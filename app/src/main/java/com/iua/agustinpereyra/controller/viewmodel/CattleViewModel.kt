package com.iua.agustinpereyra.controller.viewmodel

import android.app.Application
import androidx.lifecycle.*
import com.iua.agustinpereyra.repository.CattleRepository
import com.iua.agustinpereyra.repository.database.AppDatabase
import com.iua.agustinpereyra.repository.database.entities.Cattle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlin.coroutines.coroutineContext

class CattleViewModel(application: Application) : AndroidViewModel(application) {
    private val cattleRepository: CattleRepository = CattleRepository(application)

    // Get asynchronously
    val cattleList: LiveData<List<Cattle>> = liveData {
        val list = cattleRepository.getAll().value
        if (list != null) {
            //TODO: Is this right?
            emit(list)
        }
    }

}