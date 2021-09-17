package com.example.consumerapp.models

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class User(
    val id: Int,
    val login: String,
    @SerializedName("html_url")
    val htmlUrl: String,
    @SerializedName("avatar_url")
    val avatarUrl: String
) : Parcelable