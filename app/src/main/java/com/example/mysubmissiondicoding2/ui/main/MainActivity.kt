package com.example.mysubmissiondicoding2.ui.main

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmissiondicoding2.R
import com.example.mysubmissiondicoding2.data.model.User
import com.example.mysubmissiondicoding2.databinding.ActivityMainBinding
import com.example.mysubmissiondicoding2.helper.SettingPreferences
import com.example.mysubmissiondicoding2.helper.ViewModelFactoryMain
import com.example.mysubmissiondicoding2.ui.detail.DetailUserActivity
import com.example.mysubmissiondicoding2.ui.insert.FavoriteUserAddUpdateActivity

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class MainActivity : AppCompatActivity() {

    private lateinit var binding:ActivityMainBinding
    private lateinit var mainViewModel: MainViewModel
    private lateinit var adapter: UserAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        val pref = SettingPreferences.getInstance(dataStore)
        mainViewModel = ViewModelProvider(this, ViewModelFactoryMain(pref)).get(
            MainViewModel::class.java
        )
        adapter.setOnClickCallback(object :UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                Intent(this@MainActivity,DetailUserActivity::class.java).let {
                    it.putExtra(DetailUserActivity.EXTRA_USERNAME, data.login)
                    it.putExtra(DetailUserActivity.EXTRA_ID,data.id)
                    it.putExtra(DetailUserActivity.EXTRA_AVATAR_URL,data.avatar_url)
                    startActivity(it)
                }
            }
        })



        binding.apply {
            recyclerView2.layoutManager = LinearLayoutManager(this@MainActivity)
            recyclerView2.setHasFixedSize(true)
            recyclerView2.adapter = adapter
        }


        mainViewModel.getThemeSettings().observe(this,
            { isDarkModeActive: Boolean ->
                if (isDarkModeActive) {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                } else {
                    AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                }
            })
        mainViewModel.isLoading.observe(this,{
            showLoading(it)
        })
        mainViewModel.getSearchUser().observe(this,{
            if (it != null){
                adapter.setList(it)
                showLoading(false)
            }
        })


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.menu_item,menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu?.findItem(R.id.search)?.actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query.toString().isEmpty()){
                    return false
                }
                showLoading(true)
                mainViewModel.setSearchUser(query.toString())
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.favorite_user -> Intent(this,FavoriteUserAddUpdateActivity::class.java).also {
                startActivity(it)
            }
            R.id.action_dark_mode ->{
                mainViewModel.saveThemeSetting(true)
            }
            R.id.action_light_mode -> {
                mainViewModel.saveThemeSetting(false)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showLoading(state: Boolean){
        if (state){
            binding.pgMain.visibility = View.VISIBLE
            binding.recyclerView2.visibility = View.GONE
            binding.tvEmptyMain.visibility = View.GONE
        }else{
            binding.tvEmptyMain.visibility = View.GONE
            binding.pgMain.visibility = View.GONE
            binding.recyclerView2.visibility = View.VISIBLE
        }
    }
}