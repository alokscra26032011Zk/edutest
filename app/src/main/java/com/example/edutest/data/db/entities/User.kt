package com.example.edutest.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

const val CURRENT_USER_ID = 0

@Entity
data class User(
    @PrimaryKey(autoGenerate = false)
    var uid: Int = CURRENT_USER_ID,
    var _id:String? = null,
    var username: String? = null,
    var email: String? = null,
    var password: String? = null,
    var created_at: String? = null,
    var updated_at: String? = null,
    var role:String? = null,
    var apartment:Apartment? = null
) : Serializable