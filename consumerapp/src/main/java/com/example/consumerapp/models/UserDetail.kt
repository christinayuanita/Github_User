package com.example.consumerapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserDetail (
    val login: String,
    val name: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("avatar_url")
    val avatarUrl: String,
    val followers: Int,
    val following: Int,
    val company: String,
    val location: String,
    @SerializedName("public_repos")
    val publicRepository: Int
) : Parcelable