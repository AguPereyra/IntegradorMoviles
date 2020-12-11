package com.iua.agustinpereyra.repository.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE

@Entity(tableName = "monitored_cattle",
    primaryKeys = ["cattleCaravan", "userId"],
    foreignKeys = [
        ForeignKey(entity = Users::class,
        parentColumns = arrayOf("id"),
        childColumns = arrayOf("userId"),
        onDelete = CASCADE,
        onUpdate = CASCADE),
        ForeignKey(entity = Cattle::class,
        parentColumns = arrayOf("caravan"),
        childColumns = arrayOf("cattleCaravan"),
        onDelete = CASCADE,
        onUpdate = CASCADE)]
)
data class MonitoredCattle (
    val cattleCaravan: String,
    @ColumnInfo(index = true) // Needed for faster search
    val userId: Int)