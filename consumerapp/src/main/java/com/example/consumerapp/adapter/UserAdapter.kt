package com.example.consumerapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.consumerapp.databinding.ItemUserBinding
import com.example.consumerapp.models.User

class UserAdapter : RecyclerView.Adapter<UserAdapter.ViewHolder>() {

    private lateinit var onItemClickCallback: OnItemClickCallback
    private val userList = ArrayList<User>()

    fun setData(items: ArrayList<User>) {
        userList.clear()
        userList.addAll(items)
        notifyDataSetChanged()
    }

    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemUserBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(userList[position])
    }

    override fun getItemCount(): Int = userList.size

    inner class ViewHolder(private val binding: ItemUserBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            with(binding) {
                tvUsername.text = user.login
                tvUrl.text = user.htmlUrl
                Glide.with(itemView.context)
                    .load(user.avatarUrl)
                    .into(ivUser)
                itemView.setOnClickListener {
                    onItemClickCallback.onItemClicked(user)
                }
            }
        }
    }

    interface OnItemClickCallback {
        fun onItemClicked(data: User)
    }
}