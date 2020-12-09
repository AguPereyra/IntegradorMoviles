package com.iua.agustinpereyra.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.iua.agustinpereyra.repository.database.AppDatabase
import com.iua.agustinpereyra.repository.database.dao.MonitoredCattleDAO
import com.iua.agustinpereyra.repository.database.entities.Cattle
import com.iua.agustinpereyra.repository.database.entities.MonitoredCattle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class MonitoredCattleRepository(private val application: Application) {
    private val monitoredCattleDao: MonitoredCattleDAO
    private val _monitoredCattle = MutableLiveData<List<Cattle>>()
    val monitoredCattle: LiveData<List<Cattle>> = _monitoredCattle

    init {
        val db = AppDatabase.getDatabase(application)
        monitoredCattleDao = db.monitoredCattleDao()
    }

    /**
     * deleteMonitoredCattle removes the specified bovine from the
     * user's monitoring list. It takes care of updating the monitoredCattle
     * list, assuming that the user ID passed to update the monitored list
     * is the same that the monitoredCattle list that is being used/observed
     */
    suspend fun deleteMonitoredCattle(userId: Int, cattleId: Int) = withContext(Dispatchers.IO) {
        val bovine = MonitoredCattle(cattleId, userId)
        monitoredCattleDao.deleteMonitoredCattle(bovine)
        _monitoredCattle.value = monitoredCattleDao.getMonitoredCattleOf(userId)
    }

    /**
     * addMonitoredCattle adds the specified bovine to the user's monitoring list. It updates
     * the monitoredCattle LiveData list with the data of the same user ID that was passed to
     * add the bovine.
     */
    suspend fun addMonitoredCattle(userId: Int, cattleId: Int) = withContext(Dispatchers.IO) {
        val bovine = MonitoredCattle(cattleId, userId)
        monitoredCattleDao.insertMonitoredCattle(bovine)
        _monitoredCattle.value = monitoredCattleDao.getMonitoredCattleOf(userId)
    }

    /**
     * getAllCattleOfUser fills the monitoredCattle LiveData list with the cattle corresponding
     * to the indicated user.
     */
    suspend fun getAllCattleOfUser(userId: Int) = withContext(Dispatchers.IO) {
        _monitoredCattle.postValue(monitoredCattleDao.getMonitoredCattleOf(userId))
    }
}