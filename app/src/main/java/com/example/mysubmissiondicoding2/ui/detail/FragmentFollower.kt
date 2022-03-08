package com.example.mysubmissiondicoding2.ui.detail

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mysubmissiondicoding2.R
import com.example.mysubmissiondicoding2.databinding.FragmentFollowBinding
import com.example.mysubmissiondicoding2.ui.main.UserAdapter

class FragmentFollower : Fragment(R.layout.fragment_follow) {

    private var _binding :FragmentFollowBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel : FollowersViewModel
    private lateinit var adapter :UserAdapter
    private lateinit var username :String

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val arg = arguments
        username = arg?.getString(DetailUserActivity.EXTRA_USERNAME).toString()
        _binding = FragmentFollowBinding.bind(view)

        adapter = UserAdapter()
        adapter.notifyDataSetChanged()

        binding.apply {
            rvFollowUser.setHasFixedSize(true)
            rvFollowUser.layoutManager = LinearLayoutManager(activity)
            rvFollowUser.adapter = adapter
        }

        showLoading(true)
        viewModel = ViewModelProvider(this,ViewModelProvider.NewInstanceFactory()).get(FollowersViewModel::class.java)
        viewModel.setListFollower(username)
        viewModel.getListFollowers().observe(viewLifecycleOwner,{
            if (it != null){
                adapter.setList(it)
                showLoading(false)
            }
        })
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun showLoading(state :Boolean){
        if (state){
            binding.pgUserDetail.visibility = View.VISIBLE
        }else{
            binding.pgUserDetail.visibility = View.GONE
        }
    }
}