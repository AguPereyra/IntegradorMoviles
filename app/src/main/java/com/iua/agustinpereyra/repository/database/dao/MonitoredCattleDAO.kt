package com.iua.agustinpereyra.repository.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.iua.agustinpereyra.repository.database.entities.Cattle
import com.iua.agustinpereyra.repository.database.entities.MonitoredCattle

@Dao
interface MonitoredCattleDAO {

    @Query("SELECT cattle.id AS id, cattle.caravan AS caravan, cattle.weight AS weight, cattle.imgUrl AS imgUrl, cattle.sex AS sex " +
            "FROM cattle " +
            "INNER JOIN monitored_cattle ON cattle.id = monitored_cattle.cattleId " +
            "WHERE monitored_cattle.userId=:userId ")
    fun getMonitoredCattleOf(userId: Int): List<Cattle>

    @Insert
    fun insertMonitoredCattle(monitoredCattle: MonitoredCattle)

    @Delete
    fun deleteMonitoredCattle(monitoredCattle: MonitoredCattle)
}