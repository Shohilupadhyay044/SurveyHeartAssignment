package com.example.surveyheartassignment.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.widget.SearchView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.surveyheartassignment.OnUserClickListener
import com.example.surveyheartassignment.R
import com.example.surveyheartassignment.adapter.UserAdapter
import com.example.surveyheartassignment.data.ApiService
import com.example.surveyheartassignment.data.RetrofitHelper
import com.example.surveyheartassignment.gone
import com.example.surveyheartassignment.model.Results
import com.example.surveyheartassignment.repository.UserRepository
import com.example.surveyheartassignment.viewModel.UserViewModel
import com.example.surveyheartassignment.viewModel.UserViewModelFactory
import com.example.surveyheartassignment.visible
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : AppCompatActivity(), OnUserClickListener{
    lateinit var userViewModel: UserViewModel
    private  var userList: ArrayList<Results> = ArrayList()
    private lateinit var userAdapter: UserAdapter

    private var backPressedTime: Long = 0
    private lateinit var toast: Toast
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recycler_view.layoutManager = LinearLayoutManager(this)
        recycler_view.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))

        progressBar.visible()
        recycler_view.gone()
            setData()
            setRecyclerviewAdapter()
        swiperefresh.setOnRefreshListener {
            setData()
        }
           }


    private fun setData() {
        val apiService = RetrofitHelper.getInstance().create(ApiService::class.java)
        val userRepository = UserRepository(apiService)
        userViewModel = ViewModelProvider(this , UserViewModelFactory(userRepository))[UserViewModel::class.java]
        userViewModel.getUserList("50")
        userViewModel.users.observe(this , Observer {
           val value = it
            Log.d("TAG", it.toString())
          userAdapter.update(value)
            swiperefresh.isRefreshing = false
        })
    }

    private fun setRecyclerviewAdapter() {
         userAdapter= UserAdapter(userList,this@MainActivity)
        recycler_view.adapter = userAdapter
        progressBar.visible()
      recycler_view.visible()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        val searchItem: MenuItem? = menu?.findItem(R.id.action_search)
        val searchView: SearchView = searchItem?.actionView as SearchView

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    userAdapter.getFilter().filter(query)
                }
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    userAdapter.getFilter().filter(newText)

                }
                return false
            }

        })

        return return true
    }

    override fun onUserClickListener(results: Results) {
        val intent = Intent(this,DetailActivity::class.java)
        intent.putExtra("name","${results.name.first} ${results.name.last}")
        intent.putExtra("email" ,results.email)
        intent.putExtra("image",results.picture.large)
        intent.putExtra("address",results.location.city)
        startActivity(intent)
    }

    private fun deleteAppData() {
        try {
            Runtime.getRuntime().exec("pm clear " + packageName)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            toast.cancel()
            deleteAppData()
            super.onBackPressed()
            return
        } else {
            toast = Toast.makeText(this, "Tap 1 more time to exit", Toast.LENGTH_SHORT)
            toast.show()
        }
        backPressedTime = System.currentTimeMillis()

    }
}