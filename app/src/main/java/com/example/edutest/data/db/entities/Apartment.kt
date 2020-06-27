package com.example.edutest.data.db.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity
data class Apartment(
    @PrimaryKey(autoGenerate = true)
    var uid: Int,
    val createdAt: String,
    val _id: String,
    val name: String,
    val updatedAt: String,
    val amenities : List<Amenity>
) : Serializable