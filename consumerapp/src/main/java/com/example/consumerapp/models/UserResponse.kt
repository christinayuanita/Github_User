package com.example.consumerapp.models

import com.google.gson.annotations.SerializedName

data class UserResponse(
    @SerializedName("items")
    var user: MutableList<User?>? = null
)