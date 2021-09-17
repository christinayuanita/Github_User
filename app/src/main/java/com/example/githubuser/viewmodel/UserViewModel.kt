package com.example.githubuser.viewmodel

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.githubuser.R
import com.example.githubuser.models.User
import com.example.githubuser.models.UserDetail
import com.example.githubuser.models.UserResponse
import com.example.githubuser.network.ApiClient
import com.example.githubuser.network.ApiInterface
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*


class UserViewModel : ViewModel() {

    private val listUsers = MutableLiveData<ArrayList<User>>()
    private val user = MutableLiveData<UserDetail>()
    private val listFollowers = MutableLiveData<ArrayList<User>>()
    private val listFollowing = MutableLiveData<ArrayList<User>>()

    fun setUsers(username: String, context: Context) {
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<UserResponse> = apiService.searchUser(username)
        call.enqueue(object : Callback<UserResponse> {
            override fun onResponse(call: Call<UserResponse>, response: Response<UserResponse>) {
                if (!response.isSuccessful) {
                    Log.i("response", response.toString())
                } else if (response.body() != null) {
                    val users = ArrayList<User>(response.body()?.user)
                    listUsers.postValue(users)
                }
            }

            override fun onFailure(call: Call<UserResponse>, t: Throwable) {
                Toast.makeText(context, context.getString(R.string.check_connection), Toast.LENGTH_LONG).show()
                Log.e("failure", t.toString())
            }
        })
    }

    fun setDetailUser(username: String, context: Context) {
        val apiService = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<UserDetail?> = apiService.detailUser(username)
        call.enqueue(object : Callback<UserDetail?> {
            override fun onResponse(call: Call<UserDetail?>, response: Response<UserDetail?>) {
                if (!response.isSuccessful) {
                    Log.e("response", response.toString())
                } else if (response.body() != null) {
                    user.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<UserDetail?>, t: Throwable) {
                Toast.makeText(context, context.getString(R.string.check_connection), Toast.LENGTH_LONG).show()
                Log.e("failure", t.toString())
            }
        })
    }

    fun setFollowersUser(username: String, context: Context) {
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<ArrayList<User>> = apiService.followersUser(username)
        call.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (!response.isSuccessful) {
                    Log.i("response", response.toString())
                } else if (response.body() != null) {
                    listFollowers.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Toast.makeText(context, context.getString(R.string.check_connection), Toast.LENGTH_LONG).show()
                Log.e("failure", t.toString())
            }
        })
    }

    fun setFollowingUser(username: String, context: Context) {
        val apiService: ApiInterface = ApiClient.getClient()!!.create(ApiInterface::class.java)
        val call: Call<ArrayList<User>> = apiService.followingUser(username)
        call.enqueue(object : Callback<ArrayList<User>> {
            override fun onResponse(
                call: Call<ArrayList<User>>,
                response: Response<ArrayList<User>>
            ) {
                if (!response.isSuccessful) {
                    Log.i("response", response.toString())
                } else if (response.body() != null) {
                    listFollowing.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<ArrayList<User>>, t: Throwable) {
                Toast.makeText(context, context.getString(R.string.check_connection), Toast.LENGTH_LONG).show()
                Log.e("failure", t.toString())
            }
        })
    }

    fun getUsers(): LiveData<ArrayList<User>> = listUsers
    fun getDetailUser(): LiveData<UserDetail> = user
    fun getFollowersUser(): LiveData<ArrayList<User>> = listFollowers
    fun getFollowingUser(): LiveData<ArrayList<User>> = listFollowing
}
