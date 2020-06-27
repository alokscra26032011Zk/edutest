package com.example.edutest.data.network.responses


import com.example.edutest.data.db.entities.Owner

data class OwnerResponse(
    val isSuccessful: Boolean,
    val message: String,
    val owners: List<Owner>,
    val totalResults: Int
)