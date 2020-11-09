package com.iua.agustinpereyra.repository.database.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

/*
* Sex: False female, True male*/
@Entity(tableName = "cattle")
data class Cattle(
    @PrimaryKey
    val caravan: String,
    @NonNull
    val weight: Int,

    @NonNull
    val sex: Boolean)