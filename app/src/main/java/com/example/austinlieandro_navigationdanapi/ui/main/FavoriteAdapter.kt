package com.example.austinlieandro_navigationdanapi.ui.main

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.austinlieandro_navigationdanapi.database.FavoriteUsers
import com.example.austinlieandro_navigationdanapi.databinding.ItemPersonBinding
import com.example.austinlieandro_navigationdanapi.ui.main.DetailActivity.Companion.EXTRA_USERNAME

class FavoriteAdapter: androidx.recyclerview.widget.ListAdapter<FavoriteUsers, FavoriteAdapter.MyViewHolder>(DIFF_CALLBACK) {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteAdapter.MyViewHolder {
        val binding = ItemPersonBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteAdapter.MyViewHolder, position: Int) {
        val favorite = getItem(position)
        holder.bind(favorite)
        holder.itemView.setOnClickListener{
            val context: Context = holder.itemView.context
            val intent = Intent(context, DetailActivity::class.java)
            intent.putExtra(EXTRA_USERNAME, favorite.username)
            context.startActivity(intent)
        }
    }

    class MyViewHolder(val binding: ItemPersonBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(favorite: FavoriteUsers){
            binding.tvItem.text = "${favorite.username}"
            Glide.with(binding.imgItem)
                .load(favorite.avatarUrl)
                .into(binding.imgItem)
        }
    }

    companion object {
        val DIFF_CALLBACK = object : DiffUtil.ItemCallback<FavoriteUsers>() {
            override fun areItemsTheSame(oldItem: FavoriteUsers, newItem: FavoriteUsers): Boolean {
                return oldItem == newItem
            }
            override fun areContentsTheSame(oldItem: FavoriteUsers, newItem: FavoriteUsers): Boolean {
                return oldItem == newItem
            }
        }
    }
}