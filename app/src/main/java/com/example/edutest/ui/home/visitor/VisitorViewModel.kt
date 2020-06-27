package com.example.edutest.ui.home.visitor

import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.edutest.data.db.entities.Visitor
import com.example.edutest.data.network.responses.OwnerResponse
import com.example.edutest.data.network.responses.VisitorResponse
import com.example.edutest.data.repositories.VisitorRepository
import com.example.edutest.util.Resource
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class VisitorViewModel(
    val visitorRepository: VisitorRepository
) : ViewModel() {

    val visitors: MutableLiveData<Resource<VisitorResponse>> = MutableLiveData()
    val owners: MutableLiveData<Resource<OwnerResponse>> = MutableLiveData()
    var visitorsOffset = 0
    var visitorResponse: VisitorResponse? = null
    var lastSearchText = ""
    var apartmentid = ""


    init {
        getVisitors("")
    }


    fun getOwners(apartmentid: String) = viewModelScope.launch {
        owners.postValue(Resource.Loading())
        val response = visitorRepository.ownerList(apartmentid,"")
        Log.d("ownerres",response.toString())
        owners.postValue(handleOwnerResponse(response))
    }
    fun getVisitors(search: String) = viewModelScope.launch {
        visitors.postValue(Resource.Loading())
        if(lastSearchText != search){
            visitorsOffset = 0
            visitorResponse = null
        }
        lastSearchText = search
        val response = visitorRepository.visitorList(10,visitorsOffset,search)
        Log.d("noticeres",response.toString())
        visitors.postValue(handleVisitorResponse(response))
    }

    fun createVisitor(ownerid: String, apartmentid:String, name:String, phone:String, imageUri: Uri?, context: Context) = viewModelScope.launch {
        visitors.postValue(Resource.Loading())

        var imagePart:MultipartBody.Part? = null
        var file:File? = null
        if(imageUri != null){
            val cursor = context.contentResolver.query(imageUri,null,null,null,null)
            cursor?.moveToFirst()
            val fileName = cursor!!.getString(cursor!!.getColumnIndex(MediaStore.Files.FileColumns.DISPLAY_NAME))
            val parcelFileDescriptor =
                context.contentResolver.openFileDescriptor(imageUri!!, "r", null)

            val inputStream = FileInputStream(parcelFileDescriptor!!.fileDescriptor)
//            file = File(imageUri.path)
            file = File(context.cacheDir, fileName)
            Log.d("FN",fileName.toString())
            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)
            var imageBody = file!!.asRequestBody("multipart/form-data".toMediaTypeOrNull())
            imagePart = MultipartBody.Part.createFormData("photo", file.name, imageBody)
        }


        val nameBody = name.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val phoneBody = phone.toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val roleBody = "visitor".toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val checkinBody = "10am".toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val checkoutBody = "10pm".toRequestBody("multipart/form-data".toMediaTypeOrNull())
        val statusBody = "issued".toRequestBody("multipart/form-data".toMediaTypeOrNull())
        Log.d("IMG",imagePart.toString())
        val response = visitorRepository.visitorCreate(ownerid,apartmentid,nameBody,phoneBody,roleBody,checkinBody,checkoutBody,statusBody,imagePart)
        Log.d("visitorres",response.toString())
        visitors.postValue(handleVisitorResponse(response))
    }


    private fun handleVisitorResponse(response: Response<VisitorResponse>) : Resource<VisitorResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                visitorsOffset++
                if(visitorResponse == null) {
                    visitorResponse = resultResponse
                } else {
                    val oldArticles = visitorResponse?.visitors
                    val newArticles = resultResponse.visitors
                    oldArticles?.addAll(newArticles)
                }
                return Resource.Success(visitorResponse ?: resultResponse)
            }
        }
        return Resource.Error(response.message())
    }

    private fun handleOwnerResponse(response: Response<OwnerResponse>) : Resource<OwnerResponse> {
        if(response.isSuccessful) {
            response.body()?.let { resultResponse ->
                return Resource.Success( resultResponse)
            }
        }
        return Resource.Error(response.message())
    }



    fun saveVisitor(visitor: Visitor) = viewModelScope.launch {
        visitorRepository.saveVisitor(visitor)
    }

    fun getSavedVisitors() = visitorRepository.getVisitorsFromDao()

}