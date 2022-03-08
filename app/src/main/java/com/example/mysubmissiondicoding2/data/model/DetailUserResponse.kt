package com.example.mysubmissiondicoding2.data.model

data class DetailUserResponse (
    val login:String,
    val id:Int,
    val avatar_url:String,
    val follower_url:String,
    val following_url:String,
    val name:String,
    val followers:Int,
    val following:Int,
    val company:String,
    val location:String,
    val repos_url:String
    )