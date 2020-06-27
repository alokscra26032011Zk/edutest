package com.example.edutest.data.db.entities


import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.edutest.data.db.entities.User
import com.google.gson.annotations.SerializedName
import java.io.Serializable

@Entity
data class Visitor(
    @PrimaryKey(autoGenerate = true)
    var uid: Int,
    val apartment: String,
    val checkin: String,
    val checkout: String,
    val createdAt: String,
    val _id: String,
    val name:String,
    val owner: User,
    val status: String,
    val updatedAt: String,
    val visitor: User
) : Serializable