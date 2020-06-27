package com.example.edutest.ui.home.visitor

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import android.widget.AbsListView
import android.widget.ArrayAdapter
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

import com.example.edutest.R
import com.example.edutest.data.db.AppDatabase
import com.example.edutest.data.db.entities.Visitor
import com.example.edutest.data.network.LoginApi
import com.example.edutest.data.network.NetworkConnectionInterceptor
import com.example.edutest.data.repositories.UserRepository
import com.example.edutest.data.repositories.VisitorRepository
import com.example.edutest.ui.auth.AuthViewModel
import com.example.edutest.ui.auth.AuthViewModelFactory
import com.example.edutest.util.Constants.Companion.SEARCH_NOTICE_TIME_DELAY
import com.example.edutest.util.Resource


import kotlinx.android.synthetic.main.fragment_visitor.*
import kotlinx.coroutines.Job
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class VisitorFragment : Fragment() {

    lateinit var viewModel: VisitorViewModel
    lateinit var visitorAdapter: VisitorAdapter
    val TAG = "VisitorFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_visitor, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
                val networkConnectionInterceptor = NetworkConnectionInterceptor(requireContext())
        val api = LoginApi(networkConnectionInterceptor)
        val db = AppDatabase(requireContext())
        val repository = VisitorRepository(db)
        val factory = VisitorViewModelFactory(repository)
        viewModel = ViewModelProvider(this,factory).get(VisitorViewModel::class.java)

        val userRepository = UserRepository(api, db)
        val userFactory = AuthViewModelFactory(userRepository,requireContext())
        val userViewModel = ViewModelProvider(this,userFactory).get(AuthViewModel::class.java)

        userViewModel.getLoggedInUser().observe(viewLifecycleOwner, Observer { user ->
            Log.d("User",user._id)
            if(user != null){

            }
        })

        setupRecyclerView()


//        visitorAdapter.setOnItemClickListener {
//            val bundle = Bundle().apply {
//                putSerializable("notice", it)
//            }
//            findNavController().navigate(
//                R.id.action_nav_notice_to_nav_notice_detail,
//                bundle
//            )
//        }




        var job: Job? = null
        submitSearch.setOnClickListener {
            job?.cancel()
            job = MainScope().launch {
                    Log.d("edittext",etSearchVisitors.text.toString())
                    viewModel.getVisitors(etSearchVisitors.text.toString())

            }
        }
//        etSearchVisitors.addTextChangedListener { editable ->
//            job?.cancel()
//            job = MainScope().launch {
//                delay(SEARCH_NOTICE_TIME_DELAY)
//                editable?.let {
////                    if(editable.toString().isNotEmpty()) {
//                    Log.d("edittext",editable.toString())
//                    viewModel.getVisitors(editable.toString())
////                }
//
//                }
//            }
//        }

        viewModel.visitors.observe(viewLifecycleOwner, Observer { response ->
            when(response) {
                is Resource.Success -> {
                    hideProgressBar()
                    response.data?.let { visitorResponse ->
                        val visitorNames = getVisitorNameList(visitorResponse.visitors!!)
                        var adapter
                                = ArrayAdapter(requireContext(),
                            android.R.layout.simple_list_item_1, visitorNames)
                        etSearchVisitors.setAdapter(adapter)

                        visitorAdapter.differ.submitList(visitorResponse.visitors.toList())
                        val totalPages = visitorResponse.totalResults / 10 + 2
                        isLastPage = viewModel.visitorsOffset == totalPages
                    }
                }
                is Resource.Error -> {
                    hideProgressBar()
                    response.message?.let { message ->
                        Log.e(TAG, "An error occured: $message")
                    }
                }
                is Resource.Loading -> {
                    showProgressBar()
                }
            }
        })
    }

    private fun getVisitorNameList(visitorList: List<Visitor>) : List<String> {
        val result = ArrayList<String>()
        for(visitor in visitorList){
            result.add(visitor.name!!)
        }
        return result

    }

    private fun hideProgressBar() {
        paginationProgressBarVisitor.visibility = View.INVISIBLE
        isLoading = false
    }

    private fun showProgressBar() {
        paginationProgressBarVisitor.visibility = View.VISIBLE
        isLoading = true
    }

    var isLoading = false
    var isLastPage = false
    var isScrolling = false

    val scrollListener = object : RecyclerView.OnScrollListener() {
        override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
            super.onScrolled(recyclerView, dx, dy)

            val layoutManager = recyclerView.layoutManager as LinearLayoutManager
            val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
            val visibleItemCount = layoutManager.childCount
            val totalItemCount = layoutManager.itemCount

            val isNotLoadingAndNotLastPage = !isLoading && !isLastPage
            val isAtLastItem = firstVisibleItemPosition + visibleItemCount >= totalItemCount
            val isNotAtBeginning = firstVisibleItemPosition >= 0
            val isTotalMoreThanVisible = totalItemCount >= 10
            val shouldPaginate = isNotLoadingAndNotLastPage && isAtLastItem && isNotAtBeginning &&
                    isTotalMoreThanVisible && isScrolling
            if(shouldPaginate) {
                viewModel.getVisitors(etSearchVisitors.text.toString())
                isScrolling = false
            } else {
                rvVisitors.setPadding(0, 0, 0, 0)
            }
        }

        override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
            super.onScrollStateChanged(recyclerView, newState)
            if(newState == AbsListView.OnScrollListener.SCROLL_STATE_TOUCH_SCROLL) {
                isScrolling = true
            }
        }
    }


    private fun setupRecyclerView() {
        visitorAdapter = VisitorAdapter()
        rvVisitors.apply {
            adapter = visitorAdapter
            layoutManager = LinearLayoutManager(activity)
            addOnScrollListener(this@VisitorFragment.scrollListener)
        }
    }
}
