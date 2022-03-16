package com.example.mysubmissiondicoding2.data.model

import com.google.gson.annotations.SerializedName

data class DetailUserResponse (
//    val login:String,
//    val id:Int,
//    val avatar_url:String,
//    val follower_url:String,
//    val following_url:String,
//    val name:String,
//    val followers:Int,
//    val following:Int,
//    val company:String,
//    val location:String,
//    val repos_url:String,
    @field:SerializedName("login")
    val login:String,

    @field:SerializedName("id")
    val id:Int,

    @field:SerializedName("avatar_url")
    val avatarUrl:String,

    @field:SerializedName("follower_url")
    val followerUrl:String,

    @field:SerializedName("following_url")
    val followingUrl:String,

    @field:SerializedName("name")
    val name:String,

    @field:SerializedName("followers")
    val followers:Int,

    @field:SerializedName("following")
    val following:Int,

    @field:SerializedName("company")
    val company:String,

    @field:SerializedName("location")
    val location:String,

    @field:SerializedName("repos_url")
    val reposUrl:String,
    )