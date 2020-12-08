package com.iua.agustinpereyra.repository.database.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.iua.agustinpereyra.controller.FEMALE_FIRST_CAP
import com.iua.agustinpereyra.controller.MALE_FIRST_CAP

/*
* Sex: False female, True male*/
@Entity(tableName = "cattle")
data class Cattle(
    @PrimaryKey(autoGenerate = true)
    val cattleId: Int,
    @NonNull
    val caravan: String,
    @NonNull
    val weight: Int,
    @NonNull
    val imgUrl: String, //TODO: Do it with URL not string
    @NonNull
    val sex: Boolean) {


    fun getSexAsString() : String {
        if (sex) {
            return MALE_FIRST_CAP
        } else {
            return FEMALE_FIRST_CAP
        }
    }
}