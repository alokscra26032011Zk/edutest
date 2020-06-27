package com.example.edutest.data.repositories

import com.example.edutest.data.db.AppDatabase
import com.example.edutest.data.db.entities.Visitor
import com.example.edutest.data.network.RetrofitInstanceVisitor
import okhttp3.MultipartBody
import okhttp3.RequestBody

class VisitorRepository(
    private val db: AppDatabase
)  {
    suspend fun visitorList(limit: Int,offset: Int,search: String) =
        RetrofitInstanceVisitor.api.visitorList("",limit,offset,search )

    suspend fun ownerList(apartmentid: String,name: String) =
        RetrofitInstanceVisitor.api.ownerList(apartmentid,name )

    suspend fun visitorCreate(ownerid: String, apartmentid: String, name: RequestBody?, phone: RequestBody?, role: RequestBody?, checkin: RequestBody?, checkout: RequestBody?, status: RequestBody?,
                              photo: MultipartBody.Part?) =
        RetrofitInstanceVisitor.api.visitorCreate(ownerid,apartmentid,name,phone,role,checkin,checkout,status,photo)

    suspend fun saveVisitor(visitor: Visitor) = db.getVistorDao().upsert(visitor)

    fun getVisitorsFromDao() = db.getVistorDao().getVisitors()

}