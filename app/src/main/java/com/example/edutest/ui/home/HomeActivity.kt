package com.example.edutest.ui.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.edutest.R
import com.example.edutest.data.db.AppDatabase
import com.example.edutest.data.network.LoginApi
import com.example.edutest.data.network.NetworkConnectionInterceptor
import com.example.edutest.data.repositories.UserRepository
import com.example.edutest.ui.auth.AuthViewModel
import com.example.edutest.ui.auth.AuthViewModelFactory

import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.app_bar_main.*
import kotlinx.android.synthetic.main.bottom_navigation_view.*

class HomeActivity : AppCompatActivity() {
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        getWindow().setFlags(
//            WindowManager.LayoutParams.FLAG_FULLSCREEN,
//            WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_home)
        setSupportActionBar(toolbar)
        setupNavController()
        setupDrawerNavigation()
        setupBottomNavigation()

//        val networkConnectionInterceptor = NetworkConnectionInterceptor(this)
//        val api = LoginApi(networkConnectionInterceptor)
//        val db = AppDatabase(this)
//        val repository = UserRepository(api, db)
//        val factory = AuthViewModelFactory(repository,this)
//        val viewModel = ViewModelProvider(this,factory).get(AuthViewModel::class.java)
//        viewModel.getLoggedInUser().observe(this, Observer { user ->
//            Log.d("User",user._id)
//            if(user != null){
//                FirebaseInstanceId.getInstance().instanceId
//                    .addOnCompleteListener(OnCompleteListener { task ->
//                        if (!task.isSuccessful){
//                            return@OnCompleteListener
//                        }
//                        val token = task.result?.token
//                        if (token != null){
//                            Log.d("Token",token)
//                            viewModel.updateUserToken(user._id!!,token)
//                        }
//                    })
//            }
//        })



    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }

    private fun setupNavController() {
        navController = findNavController(R.id.nav_host_fragment)
    }

    private fun setupDrawerNavigation() {
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.nav_visitor, R.id.nav_home,
                R.id.nav_profile
            ), drawer_layout //Note that we use kotlinx.android.synthetic to get the drawerLayout reference in the xml
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        // Note that we use kotlinx.android.synthetic to get the NavigationView reference in the xml
        nav_view.setupWithNavController(navController)
        // Add this listener to the DrawerLayout because besides navigating to the
        // corresponding view we also need to mark the bottom button as checked
        nav_view.setNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.nav_visitor -> {
                    // mark the bottom button as checked
                    nav_visitor_button.isChecked = true
                    navigateTo(R.id.nav_visitor)
                }

                R.id.nav_home -> {
                    nav_visitor_create_button.isChecked = true
                    navigateTo(R.id.nav_home)
                }
                R.id.nav_profile -> {
                    nav_profile_button.isChecked = true
                    navigateTo(R.id.nav_profile)
                }

            }
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }
    }

    private fun setupBottomNavigation() {
        nav_visitor_button.setOnClickListener {
            navigateTo(R.id.nav_visitor)
        }

        nav_visitor_create_button.setOnClickListener {
            navigateTo(R.id.nav_home)
        }
        nav_profile_button.setOnClickListener {
            navigateTo(R.id.nav_profile)
        }
    }

    private fun navigateTo(resId: Int) {
        navController.navigate(resId)
    }


//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_home)
//         var prefs = application.applicationContext.getSharedPreferences(
//            application.applicationContext.getString(R.string.app_name), Context.MODE_PRIVATE)
//        var USER_TOKEN = "user_token"
//        Toast.makeText(applicationContext,"token "+prefs.getString(USER_TOKEN, null), Toast.LENGTH_LONG).show()
//
//    }
}