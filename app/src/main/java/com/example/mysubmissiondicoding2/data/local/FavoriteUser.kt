package com.example.mysubmissiondicoding2.data.local

import android.os.Parcelable
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize
import java.io.Serializable

//@Entity
//@Parcelize
//data class FavoriteUser (
//
//    @PrimaryKey
//    @ColumnInfo(name="id")
//    val id:Int,
//
//    @ColumnInfo(name="login")
//    val login:String
//
//):Parcelable
@Entity
@Parcelize
data class FavoriteUser (

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name="id")
    val id:Int,

    @ColumnInfo(name="login")
    val login:String,

    @ColumnInfo(name = "avatar_url")
    val avatarUrl:String

):Parcelable

