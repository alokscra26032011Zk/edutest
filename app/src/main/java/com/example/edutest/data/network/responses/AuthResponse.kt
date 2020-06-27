package com.example.edutest.data.network.responses

import com.example.edutest.data.db.entities.User

data class AuthResponse(
    val isSuccessful : Boolean?,
    val message: String?,
    val user: User?,
    val auth_token: String?
)