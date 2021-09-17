package com.example.githubuser.models

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("items")
    var user: MutableList<User?>? = null
)