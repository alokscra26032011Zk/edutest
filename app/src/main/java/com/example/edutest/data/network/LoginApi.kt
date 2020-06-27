package com.example.edutest.data.network

import com.example.edutest.data.network.responses.AuthResponse
import com.example.edutest.data.network.responses.OwnerResponse
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface LoginApi {

    @FormUrlEncoded
    @POST("login")
    suspend fun userLogin(
        @Field("username") username: String,
        @Field("password") password: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @POST("register")
    suspend fun userSignup(
        @Field("username") username: String,
        @Field("email") email: String,
        @Field("password") password: String,
        @Field("role") role: String,
        @Field("apartmentId") apartmentId: String
    ) : Response<AuthResponse>

    @FormUrlEncoded
    @PUT("update")
    suspend fun userUpdateToken(
        @Header("userid") userid:String,
        @Field("fcmToken") fcmToken: String
    ) : Response<AuthResponse>


    companion object{
        operator fun invoke(
            networkConnectionInterceptor: NetworkConnectionInterceptor
        ) : LoginApi{

            val okkHttpclient = OkHttpClient.Builder()
                .addInterceptor(networkConnectionInterceptor)
                .build()

            return Retrofit.Builder()
                .client(okkHttpclient)
//                .baseUrl("http://10.0.2.2:1234/user/")
                .baseUrl("http://ec2-3-20-203-149.us-east-2.compute.amazonaws.com/user/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(LoginApi::class.java)
        }
    }

}