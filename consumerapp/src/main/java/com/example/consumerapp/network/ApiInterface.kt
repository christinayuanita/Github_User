package com.example.consumerapp.network

import com.example.consumerapp.BuildConfig
import com.example.consumerapp.models.User
import com.example.consumerapp.models.UserDetail
import com.example.consumerapp.models.UserResponse
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET("/search/users")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun searchUser(
        @Query("q") username: String?
    ): Call<UserResponse>

    @GET("/users/{username}")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun detailUser(
        @Path("username") username: String?
    ): Call<UserDetail?>

    @GET("/users/{username}/followers")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun followersUser(
        @Path("username") username: String?
    ): Call<ArrayList<User>>

    @GET("/users/{username}/following")
    @Headers("Authorization: ${BuildConfig.GITHUB_TOKEN}")
    fun followingUser(
        @Path("username") username: String?
    ): Call<ArrayList<User>>
}