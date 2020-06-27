package com.example.edutest.ui.auth

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.View
import androidx.lifecycle.ViewModel
import com.example.edutest.R
import com.example.edutest.data.repositories.UserRepository
import com.example.edutest.ui.home.HomeActivity
import com.example.edutest.util.ApiException
import com.example.edutest.util.Coroutines
import com.example.edutest.util.NoInternetException

class AuthViewModel(
    private val repository: UserRepository,
    private val context: Context
) : ViewModel() {


    var username: String? = null
    var email: String? = null
    var password: String? = null
    var passwordconfirm: String? = null
    var role = "resident"
    var apartmentId = "5eac961f92e59c500b0008bf"

    var authListener: AuthListener? = null

    fun getLoggedInUser() = repository.getUser()

    fun updateUserToken(userid:String,token:String){
        Coroutines.main {
            try {
                val authResponse = repository.userUpdateToken(userid!!,token!!)
                authResponse.user?.let {
                    repository.saveUser(it)
                    return@main
                }

            }catch(e: ApiException){

            }catch (e: NoInternetException){

            }
        }

    }
    fun onLoginButtonClick(view: View){
        authListener?.onStarted()
        if(email.isNullOrEmpty() || password.isNullOrEmpty()){
            authListener?.onFailure("Invalid email or password")
            return
        }

        Coroutines.main {
            try {
                val authResponse = repository.userLogin(email!!, password!!)
                authResponse.auth_token?.let {
                    var prefs = context.getSharedPreferences(
                        context.applicationContext.getString(R.string.app_name), Context.MODE_PRIVATE)
                    val USER_TOKEN = "user_token"
                    val editor = prefs.edit()
                    editor.putString(USER_TOKEN, it)
                    editor.apply()
                    Log.d("token",it)
                }
                authResponse.user?.let {
                    authListener?.onSuccess(it)

                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
            }catch(e: ApiException){
                authListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                authListener?.onFailure(e.message!!)
            }
        }

    }

    fun onLogin(view: View){
        Intent(view.context, HomeActivity::class.java).also {
            view.context.startActivity(it)
        }
    }

    fun onSignup(view: View){
        Intent(view.context, SignupActivity::class.java).also {
            view.context.startActivity(it)
        }
    }


    fun onSignupButtonClick(view: View){
        authListener?.onStarted()

        if(username.isNullOrEmpty()){
            authListener?.onFailure("Name is required")
            return
        }

        if(email.isNullOrEmpty()){
            authListener?.onFailure("Email is required")
            return
        }

        if(password.isNullOrEmpty()){
            authListener?.onFailure("Please enter a password")
            return
        }

        if(password != passwordconfirm){
            authListener?.onFailure("Password did not match")
            return
        }


        Coroutines.main {
            try {
                Log.d("in try","in try")
                val authResponse = repository.userSignup(username!!, email!!, password!!, role!!, apartmentId!!)
                Log.d("in try res",authResponse.message)
                authResponse.user?.let {
                    Log.d("user",it.toString())
                    authListener?.onSuccess(it)
                    repository.saveUser(it)
                    return@main
                }
                authListener?.onFailure(authResponse.message!!)
                Log.d("in try error",authResponse.message)
            }catch(e: ApiException){
                Log.d("in catch api","in catch api")
                authListener?.onFailure(e.message!!)
            }catch (e: NoInternetException){
                Log.d("in catch network","in catch network")
                authListener?.onFailure(e.message!!)
            }
        }

    }

}