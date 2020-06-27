package com.example.edutest.data.network.responses


import com.example.edutest.data.db.entities.Visitor

data class VisitorResponse(
    val isSuccessful: Boolean,
    val message: String,
    val totalResults: Int,
    val visitors: MutableList<Visitor>
)