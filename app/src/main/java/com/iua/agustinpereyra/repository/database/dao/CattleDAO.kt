package com.iua.agustinpereyra.repository.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.iua.agustinpereyra.repository.database.entities.Cattle

@Dao
interface CattleDAO {
    @Query("SELECT * FROM cattle")
    fun getAll() : LiveData<List<Cattle>>

    @Query("DELETE FROM cattle")
    fun deleteAll()

    @Update
    fun updateAll(updatedCattle: List<Cattle>)

    @Insert
    fun insert(cattle: Cattle)

    @Query("DELETE FROM cattle WHERE cattle.caravan=:caravan")
    fun delete(caravan: String)

    // Update based on caravan (not primary key)
    @Query("UPDATE cattle SET weight=:weight, imgUrl=:imgUrl " +
            "WHERE caravan=:caravan")
    fun update(caravan: String, weight: Int, imgUrl: String)
}