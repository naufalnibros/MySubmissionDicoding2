package com.example.mysubmissiondicoding2.ui.detail

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.annotation.StringRes
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mysubmissiondicoding2.R
import com.example.mysubmissiondicoding2.data.model.User
import com.example.mysubmissiondicoding2.databinding.ActivityDetailUserBinding
import com.example.mysubmissiondicoding2.ui.main.UserAdapter
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

class DetailUserActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private lateinit var viewModel :DetailUserViewModel
    private lateinit var adapter: UserAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail_user)
        val username = intent.getStringExtra(EXTRA_USERNAME)
        val mBundle = Bundle()
        mBundle.putString(EXTRA_USERNAME,username)

        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        adapter = UserAdapter()
        viewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(DetailUserViewModel::class.java)

        adapter.setOnClickCallback(object :UserAdapter.OnItemClickCallback{
            override fun onItemClicked(data: User) {
                startActivity(getIntent(this@DetailUserActivity,username.toString()))
            }
        })
        viewModel.setUserData(username.toString())
        viewModel.getUserData().observe(this,{
            if (it != null){
                binding.apply {
                    tvNamaUserDetail.text = it.name
                    tvUsernameDetail.text = it.login
                    tvLocation.text = it.location
                    tvFrameFollowers.text = "${it.followers} Followers"
                    tvFrameFollowing.text = "${it.following} Following"
                    tvWork.text = it.company

                    Glide.with(this@DetailUserActivity)
                        .load(it.avatar_url)
                        .transition(DrawableTransitionOptions.withCrossFade())
                        .centerCrop()
                        .into(imageProfile)
                }
            }
        })

        val sectionPagerAdapter  = SectionPagerAdapter(this,mBundle)
        val viewPager : ViewPager2 = findViewById(R.id.view_pager_detail_profile)
        viewPager.adapter = sectionPagerAdapter
        val tabs : TabLayout = findViewById(R.id.tabLayout)
        TabLayoutMediator(tabs,viewPager){tab,position ->
            tab.text = resources.getString(TAB_TITLE[position])
        }.attach()

        supportActionBar?.elevation = 0f

    }

    fun getIntent(context: Context,username:String): Intent{
        val intent = Intent(context,DetailUserActivity::class.java)
        intent.putExtra(EXTRA_USERNAME,username)
        return intent
    }

    companion object{
        var EXTRA_USERNAME = "extra_username"
        @StringRes
        private  val TAB_TITLE = intArrayOf(
            R.string.tab_detail_1,
            R.string.tab_detail_2,
        )
    }
}