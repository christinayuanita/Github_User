package com.example.githubuser.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.githubuser.databinding.ItemFavoriteUserBinding
import com.example.githubuser.models.User

class FavoriteUserAdapter : RecyclerView.Adapter<FavoriteUserAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val favoriteList = ArrayList<User>()

    fun setData(items: ArrayList<User>) {
        favoriteList.clear()
        favoriteList.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    fun removeItem(position: Int) {
        this.favoriteList.removeAt(position)
        notifyItemRemoved(position)
        notifyItemRangeChanged(position, this.favoriteList.size)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemFavoriteUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(favoriteList[position])
    }

    override fun getItemCount(): Int = favoriteList.size

    inner class ViewHolder(private val binding: ItemFavoriteUserBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(user: User) {
            with(binding) {
                tvUsername.text = user.login
                tvUrl.text = user.htmlUrl
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .into(ivUser)
                btnRemove.setOnClickListener {
                    onItemClickCallback.onRemoveClicked(user, bindingAdapterPosition)
                }
                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(user)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
        fun onRemoveClicked(data: User, position: Int)
    }

}