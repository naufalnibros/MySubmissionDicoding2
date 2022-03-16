package com.example.mysubmissiondicoding2.ui.insert

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mysubmissiondicoding2.data.local.FavoriteUser
import com.example.mysubmissiondicoding2.databinding.ItemFavUserBinding
import com.example.mysubmissiondicoding2.helper.NoteDiffCallback
import com.example.mysubmissiondicoding2.ui.main.UserAdapter

class FavoriteUserAdapter : RecyclerView.Adapter<FavoriteUserAdapter.FavUserViewHolder>() {
    private val listFavUser = ArrayList<FavoriteUser>()

    private lateinit var onItemClickCallback : OnItemClickCallback

    fun setListFavUsers(listNotes: List<FavoriteUser>) {
        val diffCallback = NoteDiffCallback(this.listFavUser, listNotes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        this.listFavUser.clear()
        this.listFavUser.addAll(listNotes)
        diffResult.dispatchUpdatesTo(this)
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavUserViewHolder {
        val binding = ItemFavUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return FavUserViewHolder(binding)
    }
    override fun onBindViewHolder(holder: FavUserViewHolder, position: Int) {
        holder.bind(listFavUser[position])
    }
    override fun getItemCount(): Int {
        return listFavUser.size
    }
    inner class FavUserViewHolder(private val binding: ItemFavUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteUser: FavoriteUser) {
            with(binding) {
                tvItemUsername.text = favoriteUser.login
//                tvItemDate.text = favoriteUser
                tvItemId.text = favoriteUser.id.toString()
                cvItemFavUser.setOnClickListener {
                    val intent = Intent(it.context, FavoriteUserAddUpdateActivity::class.java)
                    intent.putExtra(FavoriteUserAddUpdateActivity.EXTRA_USERNAME, favoriteUser)
                    it.context.startActivity(intent)
                }
            }
        }
    }

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    interface OnItemClickCallback{
        fun onItemClicked(data: FavoriteUser)
    }
}