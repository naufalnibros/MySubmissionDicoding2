package com.example.mysubmissiondicoding2.ui.main

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.example.mysubmissiondicoding2.data.model.User
import com.example.mysubmissiondicoding2.databinding.ItemUserBinding

class UserAdapter:RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private val listUser = ArrayList<User>()
    private lateinit var onItemClickCallback : OnItemClickCallback

    @SuppressLint("NotifyDataSetChanged")
    fun setList(users:ArrayList<User>){
        listUser.clear()
        listUser.addAll(users)
        notifyDataSetChanged()
    }

    fun setOnClickCallback(onItemClickCallback: OnItemClickCallback){
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int):ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(viewGroup.context),viewGroup,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.bind(listUser[position])
    }

    override fun getItemCount() = listUser.size

    inner class ViewHolder(private val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user:User){
            binding.apply {
                Glide.with(itemView)
                    .load(user.avatar_url)
                    .transition(DrawableTransitionOptions.withCrossFade())
                    .centerCrop()
                    .into(circleImageView)
                tvNamaUser.text = user.login
                tvUsername.text = user.id.toString()

                root.setOnClickListener {
                    onItemClickCallback.onItemClicked(user)
                }
            }
        }

//        fun set
    }

    interface OnItemClickCallback{
        fun onItemClicked(data:User)
    }
}