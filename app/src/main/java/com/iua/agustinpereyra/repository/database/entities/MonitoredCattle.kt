package com.iua.agustinpereyra.repository.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "monitored_cattle",
    primaryKeys = ["cattleId", "userId"],
    foreignKeys = [
        ForeignKey(entity = Users::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("userId"),
        onDelete = CASCADE,
        onUpdate = CASCADE),
        ForeignKey(entity = Cattle::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("cattleId"),
        onDelete = CASCADE,
        onUpdate = CASCADE)]
)
data class MonitoredCattle (
    val cattleId: Int,
    @ColumnInfo(index = true) // Needed for faster search
    val userId: Int)