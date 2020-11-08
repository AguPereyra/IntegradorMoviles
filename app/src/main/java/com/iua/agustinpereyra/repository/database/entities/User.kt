package com.iua.agustinpereyra.repository.database.entities

import androidx.annotation.NonNull
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "user")
class User {

    @PrimaryKey
    private lateinit var email : String
    @NonNull
    private lateinit var username : String
    @NonNull
    private lateinit var passwd : String

}
