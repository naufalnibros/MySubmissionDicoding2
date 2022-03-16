package com.example.mysubmissiondicoding2.ui.main

import android.util.Log
import androidx.lifecycle.*
import com.example.mysubmissiondicoding2.api.ApiConfig
import com.example.mysubmissiondicoding2.data.model.User
import com.example.mysubmissiondicoding2.data.model.UserResponse
import com.example.mysubmissiondicoding2.helper.SettingPreferences
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val pref: SettingPreferences):ViewModel() {

    val listUser= MutableLiveData<ArrayList<User>>()

    private val _isLoading = MutableLiveData<Boolean>()
    val isLoading : LiveData<Boolean> get() = _isLoading

   fun getThemeSettings(): LiveData<Boolean> {
        return pref.getThemeSetting().asLiveData()
    }

    fun saveThemeSetting(isDarkModeActive: Boolean) {
        viewModelScope.launch {
            pref.saveThemeSetting(isDarkModeActive)
        }
    }
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
                        _isLoading.value = false
                    }
                }

                override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                    Log.d("onFailure",t.message.toString())
                    _isLoading.value = true
                }
            })
    }

    fun getSearchUser(): LiveData<ArrayList<User>> {
        return listUser
    }

//    fun getThemeSettings(): LiveData<Boolean> {
//        return pref.getThemeSetting().asLiveData()
//    }
//
//    fun saveThemeSetting(isDarkModeActive: Boolean) {
//        viewModelScope.launch {
//            pref.saveThemeSetting(isDarkModeActive)
//        }
//    }
}