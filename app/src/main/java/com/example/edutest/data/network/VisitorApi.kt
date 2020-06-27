package com.example.edutest.data.network

import com.example.edutest.data.db.entities.Apartment
import com.example.edutest.data.network.responses.OwnerResponse
import com.example.edutest.data.network.responses.VisitorResponse
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.*

interface VisitorApi {

    @FormUrlEncoded
    @POST("list")
    suspend fun visitorList(
        @Field("visitor") visitor:String,
        @Query("limit") limit: Int,
        @Query("offset") offset: Int,
        @Query("search") search: String
    ) : Response<VisitorResponse>

    @FormUrlEncoded
    @POST("ownerlist")
    suspend fun ownerList(
        @Header("apartmentid") apartmentid: String,
        @Field("name") name: String
    ) : Response<OwnerResponse>

    @Multipart
    @POST("create")
    suspend fun visitorCreate(
        @Header("ownerid") ownerid: String,
        @Header("apartmentid") apartmentid: String,
        @Part("name") name: RequestBody?,
        @Part("phone") phone: RequestBody?,
        @Part("role") role: RequestBody?,
        @Part("checkin") checkin: RequestBody?,
        @Part("checkout") checkout: RequestBody?,
        @Part("status") status: RequestBody?,
        @Part photo: MultipartBody.Part?
    ) : Response<VisitorResponse>

}