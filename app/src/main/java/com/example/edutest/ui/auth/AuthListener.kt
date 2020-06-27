package com.example.edutest.ui.auth

import com.example.edutest.data.db.entities.User

interface AuthListener {
    fun onStarted()
    fun onSuccess(user: User)
    fun onFailure(message: String)
}