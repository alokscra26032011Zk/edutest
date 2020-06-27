package com.example.edutest.data.db.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Owner(
    @PrimaryKey(autoGenerate = true)
    var uid: Int,
    val apartment: String,
    val createdAt: String,
    val email: String,
    val _id: String,
    val password: String,
    val phone: String,
    val photo: String,
    val role: String,
    val staffs: List<Any>,
    val updatedAt: String,
    val username: String,
    val name: String

) : Serializable