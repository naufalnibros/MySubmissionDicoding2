package com.example.mysubmissiondicoding2.ui.detail

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.example.mysubmissiondicoding2.R
import com.example.mysubmissiondicoding2.data.local.FavoriteUser
import com.example.mysubmissiondicoding2.databinding.ActivityDetailUserBinding
import com.example.mysubmissiondicoding2.helper.ViewModelFactory
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private var _isChecked = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val id = intent.getIntExtra(EXTRA_ID,0)
        val avatarUrl = intent.getStringExtra(EXTRA_AVATAR_URL)
        val mBundle = Bundle()
        mBundle.putString(EXTRA_USERNAME,username)

        val favoriteUser = FavoriteUser(id,username.toString(),avatarUrl.toString())
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val viewModel = obtainViewModel(this)
        viewModel.setUserData(username.toString())
        viewModel.isLoading.observe(this,{
            showLoadingDetail(it)
        })

        viewModel.checkUser(id).observe(this, {
            if (it == 0) {
                binding.btnFavourite.isChecked = false
                _isChecked = false
            }else{
                binding.btnFavourite.isChecked = true
                _isChecked = true
            }
        })

        viewModel.getUserData().observe(this,{
                binding.apply {
                    tvNamaUserDetail.text = it.name
                    tvUsernameDetail.text = it.login
                    if (it?.location != null){
                        tvLocation.text = it.location
                    }else{ tvLocation.text = "-" }

                    if (it?.company != null){
                        tvWork.text = it.company
                    }else{ tvWork.text = "-" }

                    if (it?.reposUrl != null){
                        tvRepository.text = it.reposUrl
                    }else{ tvRepository.text = "-" }

                    if (it?.followers != null){
                        tvFrameFollowers.text = "${it.followers} Followers"
                    }else{ tvFrameFollowers.text = "-" }

                    if (it?.following != null){
                        tvFrameFollowing.text = "${it.following} Followers"
                    }else{ tvFrameFollowing.text = "-" }

                    Glide.with(this@DetailUserActivity)
                        .load(it.avatarUrl)
                        .circleCrop()
                        .into(imageProfile)
            }
        })

        binding.btnFavourite.setOnClickListener {
            _isChecked = !_isChecked
            if (_isChecked){
                viewModel.insert(favoriteUser)
                Toast.makeText(this,"Data favorite ditambahkan",Toast.LENGTH_SHORT).show()
            }else{
                viewModel.delete(favoriteUser)
                Toast.makeText(this,"Data favorite dihapus",Toast.LENGTH_SHORT).show()
            }
            binding.btnFavourite.isChecked = _isChecked
        }

        val sectionPagerAdapter  = SectionPagerAdapter(this,mBundle)
        val viewPager : ViewPager2 = findViewById(R.id.view_pager_detail_profile)
        val tabs : TabLayout = findViewById(R.id.tabLayout)
        viewPager.adapter = sectionPagerAdapter
        TabLayoutMediator(tabs,viewPager){tab,position ->
            tab.text = resources.getString(TAB_TITLE[position])
        }.attach()
        supportActionBar?.elevation = 0f
    }

    private fun obtainViewModel(activity: AppCompatActivity): DetailUserViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(DetailUserViewModel::class.java)
    }

    private fun showLoadingDetail(state: Boolean){
        if (state){
            binding.pgDetail.visibility = View.VISIBLE
        }else{
            binding.pgDetail.visibility = View.GONE
        }
    }


    companion object{
        var EXTRA_USERNAME = "extra_username"
        var EXTRA_ID = "extra_id"
        var EXTRA_AVATAR_URL = "extra_avatar_url"
        @StringRes
        private  val TAB_TITLE = intArrayOf(
            R.string.tab_detail_1,
            R.string.tab_detail_2,
        )
    }
}