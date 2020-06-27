package com.example.edutest.data.network

import com.example.edutest.util.Constants.Companion.BASE_URL
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstanceVisitor (path:String) {
    companion object {

        private val retrofit by lazy {

            val client = OkHttpClient.Builder()
                .build()
            Retrofit.Builder()
                .baseUrl(BASE_URL+"/visitor/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
        }

        val api by lazy {
            retrofit.create(VisitorApi::class.java)
        }
    }
}