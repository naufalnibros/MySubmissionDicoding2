package com.example.mysubmissiondicoding2.ui.main

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mysubmissiondicoding2.data.Api.ApiConfig
import com.example.mysubmissiondicoding2.data.model.User
import com.example.mysubmissiondicoding2.data.model.UserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel:ViewModel() {

    val listUser= MutableLiveData<ArrayList<User>>()

    fun setSearchUser(query:String){
        ApiConfig.getApiService()
            .getUser(query)
            .enqueue(object : Callback<UserResponse>{
                override fun onResponse(
                    call: Call<UserResponse>,
                    response: Response<UserResponse>,
                ) {
                    if (response.isSuccessful){
                        listUser.postValue(response.body()?.items)
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("onFailure",t.message.toString())
                }
            })
    }

    fun getSearchUser(): LiveData<ArrayList<User>> {
        return listUser
    }
}