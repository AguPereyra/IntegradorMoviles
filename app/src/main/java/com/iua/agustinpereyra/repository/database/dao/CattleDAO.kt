package com.iua.agustinpereyra.repository.database.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.iua.agustinpereyra.repository.database.entities.Cattle

@Dao
interface CattleDAO {
    @Query("SELECT * FROM cattle")
    fun getAll() : LiveData<List<Cattle>>

    @Query("DELETE FROM cattle")
    fun deleteAll()

    @Insert
    fun insert(cattle: Cattle)
}