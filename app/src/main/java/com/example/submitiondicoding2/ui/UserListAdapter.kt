package com.example.submitiondicoding2.ui

import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.submitiondicoding2.data.response.PersonUser
import com.example.submitiondicoding2.databinding.ItemLayoutUserBinding

class UserListAdapter: ListAdapter<PersonUser, UserListAdapter.UserViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewtype:Int): UserViewHolder{
        val binding = ItemLayoutUserBinding.inflate(LayoutInflater.from(parent.context),parent, false)
        return UserViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UserViewHolder, position: Int) {
        val user = getItem(position)
        holder.bind(user)


        holder.itemView.setOnClickListener{
            val moveDetail = Intent(holder.itemView.context, DetailUser::class.java )
            moveDetail.putExtra(DetailUser.USERNAME, user.login)
            holder.itemView.context.startActivity(moveDetail)
        }
    }

    class UserViewHolder(private val binding: ItemLayoutUserBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(user: PersonUser){
            binding.tvItemName.setText(user.login)
            Glide.with(binding.imgItemPhoto.context).load(user.avatarUrl).into(binding.imgItemPhoto)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<PersonUser>() {
            override fun areItemsTheSame(oldItem: PersonUser, newItem: PersonUser): Boolean {
                return oldItem == newItem
            }

            override fun areContentsTheSame(oldItem: PersonUser, newItem: PersonUser): Boolean {
                return oldItem == newItem
            }
        }
}}