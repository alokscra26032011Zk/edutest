package com.example.edutest.data.repositories

import android.util.Log
import com.example.edutest.data.db.AppDatabase
import com.example.edutest.data.db.entities.User
import com.example.edutest.data.network.LoginApi
import com.example.edutest.data.network.SafeApiRequest
import com.example.edutest.data.network.responses.AuthResponse

class UserRepository(
    private val api: LoginApi,
    private val db: AppDatabase
) : SafeApiRequest() {

    suspend fun userLogin(email: String, password: String): AuthResponse {
        return apiRequest { api.userLogin(email, password) }
    }

    suspend fun userSignup(
        username: String,
        email: String,
        password: String,
        role: String,
        apartmentId:String
    ) : AuthResponse {
        Log.d("repo","in repo")
        return apiRequest{ api.userSignup(username, email, password, role, apartmentId)}
    }

    suspend fun userUpdateToken(
        userid: String,
        token:String
    ) : AuthResponse {
        Log.d("repo","in repo")
        return apiRequest{ api.userUpdateToken(userid,token)}
    }

    suspend fun saveUser(user: User) = db.getUserDao().upsert(user)

    fun getUser() = db.getUserDao().getuser()

}