package com.example.mysubmissiondicoding2.data.Api

import com.example.mysubmissiondicoding2.data.model.DetailUserResponse
import com.example.mysubmissiondicoding2.data.model.User
import com.example.mysubmissiondicoding2.data.model.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiService {
    @GET("search/users")
    @Headers("Authorization: token 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")
    fun getUser(
        @Query("q") query:String
    ):Call<UserResponse>

    @GET("users/{username}")
    @Headers("Authorization: token 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")
    fun getDetailUser(
        @Path("username") username:String
    ):Call<DetailUserResponse>

    @GET("users/{username}/followers")
    @Headers("Authorization: token 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")
    fun getFollowers(
        @Path("username") username: String
    ):Call<ArrayList<User>>

    @GET("users/{username}/following")
    @Headers("Authorization: token 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")
    fun getFollowing(
        @Path("username") username: String
    ):Call<ArrayList<User>>


}