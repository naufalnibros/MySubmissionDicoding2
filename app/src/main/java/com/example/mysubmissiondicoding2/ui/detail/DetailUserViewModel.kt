package com.example.mysubmissiondicoding2.ui.detail

import android.app.Application
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.mysubmissiondicoding2.api.ApiConfig
import com.example.mysubmissiondicoding2.data.local.FavoriteUser
import com.example.mysubmissiondicoding2.data.local.FavoriteUserRepository
import com.example.mysubmissiondicoding2.data.model.DetailUserResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DetailUserViewModel(application: Application) : ViewModel() {

    private val user = MutableLiveData<DetailUserResponse>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading: LiveData<Boolean> get() = _isLoading

    private val mFavoriteUserRepository: FavoriteUserRepository = FavoriteUserRepository(application)

    fun setUserData(username: String) {
        ApiConfig.getApiService()
            .getDetailUser(username)
            .enqueue(object : Callback<DetailUserResponse> {
                override fun onResponse(
                    call: Call<DetailUserResponse>,
                    response: Response<DetailUserResponse>,
                ) {
                    if (response.isSuccessful) {
                        user.postValue(response.body())
                        _isLoading.value = false

                    } else {
                        Log.d("onFailure", response.message().toString())
                        _isLoading.value = true
                    }
                }

                override fun onFailure(call: Call<DetailUserResponse>, t: Throwable) {
                    Log.d("onFailure", t.message.toString())
                    _isLoading.value = true
                }
            })
    }

    fun getUserData(): LiveData<DetailUserResponse> {
        return user
    }

    fun getAllFavUser():LiveData<List<FavoriteUser>> = mFavoriteUserRepository.getAllFavoriteUser()
    fun insert(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.insert(favoriteUser)
    }
    fun delete(favoriteUser: FavoriteUser) {
        mFavoriteUserRepository.delete(favoriteUser)
    }
    fun checkUser(favoriteUser: FavoriteUser){
        mFavoriteUserRepository.checkUser(favoriteUser)
    }

}