package com.example.mysubmissiondicoding2.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmissiondicoding2.R
import com.example.mysubmissiondicoding2.data.model.User
import com.example.mysubmissiondicoding2.databinding.ActivityMainBinding
import com.example.mysubmissiondicoding2.ui.detail.DetailUserActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        mainViewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(MainViewModel::class.java)

        adapter.setOnClickCallback(object :UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity,DetailUserActivity::class.java).let {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    startActivity(it)
                }
            }
        })

        binding.apply {
            recyclerView2.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerView2.setHasFixedSize(true)
            recyclerView2.adapter = adapter

            btnSearch.setOnClickListener { searchUsername() }

            etQuery.setOnKeyListener { _, keyCode, event ->
                if (event.action == KeyEvent.ACTION_DOWN || keyCode == KeyEvent.KEYCODE_ENTER){
                    searchUsername()
                    return@setOnKeyListener true
                }
                return@setOnKeyListener false
            }
        }

        mainViewModel.getSearchUser().observe(this,{
            if (it != null){
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    private fun searchUsername(){
        binding.apply {
            val query = etQuery.text.toString()
            if (query.isEmpty())return
            showLoading(true)
            mainViewModel.setSearchUser(query)
        }
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.pgMain.visibility = View.VISIBLE
        }else{
            binding.pgMain.visibility = View.GONE
        }
    }
}