package com.example.submitiondicoding2.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submitiondicoding2.data.response.GetFollowItem
import com.example.submitiondicoding2.databinding.ItemLayoutUserBinding

class UserListFollowAdapter: ListAdapter<GetFollowItem, UserListFollowAdapter.UserViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewtype:Int): UserListFollowAdapter.UserViewHolder {
        val binding = ItemLayoutUserBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)
    }

    class UserViewHolder(private val binding: ItemLayoutUserBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: GetFollowItem){
            binding.tvItemName.setText(user.login)
            Glide.with(binding.imgItemPhoto.context).load(user.avatarUrl).into(binding.imgItemPhoto)
        }
    }

    companion object {

        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<GetFollowItem>() {
            override fun areItemsTheSame(oldItem: GetFollowItem, newItem: GetFollowItem): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: GetFollowItem, newItem: GetFollowItem): Boolean {
                return oldItem == newItem
            }
        }
    }
}