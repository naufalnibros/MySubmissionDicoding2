package com.example.mysubmissiondicoding2.ui.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mysubmissiondicoding2.data.Api.ApiConfig
import com.example.mysubmissiondicoding2.data.model.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel: ViewModel() {

    private val user = MutableLiveData<DetailUserResponse>()

    fun setUserData(username : String){
        ApiConfig.getApiService()
            .getDetailUser(username)
            .enqueue(object :Callback<DetailUserResponse>{
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>,
                ) {
                    if (response.isSuccessful){
                        user.postValue(response.body())
                    }else{
                        Log.d("onFailure",response.message().toString())
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("onFailure",t.message.toString())
                }
            })
    }

    fun getUserData():LiveData<DetailUserResponse>{
        return user
    }
}