package com.example.edutest.data.db

import androidx.room.TypeConverter
import com.example.edutest.data.db.entities.Amenity
import com.example.edutest.data.db.entities.Apartment
import com.example.edutest.data.db.entities.User
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class Converters {
    private val gson = Gson()


    @TypeConverter
    fun toAmenityList(data: String?): List<Amenity> {
        if (data == null) {
            return emptyList()
        }
        val listType = object : TypeToken<List<Amenity>>(){}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromAmenityList(amenities: List<Amenity>): String {
        return gson.toJson(amenities)
    }

    @TypeConverter
    fun fromAmenity(amenity: Amenity): String {
        return gson.toJson(amenity)
    }
    @TypeConverter
    fun toAmenity(data: String?): Amenity {
//        if (data == null) {
//            return emptyList()
//        }
        val listType = object : TypeToken<Amenity>(){}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromApartment(apartment: Apartment): String {
        return gson.toJson(apartment)
    }
    @TypeConverter
    fun toApartment(data: String?): Apartment {
//        if (data == null) {
//            return emptyList()
//        }
        val listType = object : TypeToken<Apartment>(){}.type
        return gson.fromJson(data, listType)
    }

    @TypeConverter
    fun fromUser(author: User): String {
        return gson.toJson(author)
    }
    @TypeConverter
    fun toUser(data: String?): User {
//        if (data == null) {
//            return emptyList()
//        }
        val listType = object : TypeToken<User>(){}.type
        return gson.fromJson(data, listType)
    }



}