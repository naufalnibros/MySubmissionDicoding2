package com.example.mysubmissiondicoding2.helper

import androidx.recyclerview.widget.DiffUtil
import com.example.mysubmissiondicoding2.data.local.FavoriteUser

class NoteDiffCallback(private val mOldFavList: List<FavoriteUser>, private val mNewFavList: List<FavoriteUser>) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return mOldFavList.size
    }
    override fun getNewListSize(): Int {
        return mNewFavList.size
    }
    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return mOldFavList[oldItemPosition].id == mNewFavList[newItemPosition].id
    }
    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = mOldFavList[oldItemPosition]
        val newEmployee = mNewFavList[newItemPosition]
        return oldEmployee.login == newEmployee.login && oldEmployee.id == newEmployee.id
    }
}