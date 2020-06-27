package com.example.edutest.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.edutest.data.db.entities.Visitor

@Dao
interface VisitorDao{

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(visitor: Visitor) : Long

    @Query("SELECT * FROM visitor")
    fun getVisitors() : LiveData<Visitor>

}