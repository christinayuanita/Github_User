package com.example.githubuser.viewmodel

import android.content.Context
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.githubuser.database.DatabaseContract.FavoriteUserColumns.Companion.CONTENT_URI
import com.example.githubuser.helper.MappingHelper
import com.example.githubuser.models.User
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

class FavoriteViewModel : ViewModel(){

    private val listFavoriteUsers = MutableLiveData<ArrayList<User>>()

    fun setFavoriteUsers(context: Context) {
        viewModelScope.launch(Dispatchers.Main) {
            try {
                val deferredNotes = async(Dispatchers.IO) {
                    val cursor = context.contentResolver.query(CONTENT_URI, null, null, null, null)
                    MappingHelper.mapCursorToArrayList(cursor)
                }
                val favoriteUsers = deferredNotes.await()
                listFavoriteUsers.postValue(favoriteUsers)
            }catch (e: Exception){
                Toast.makeText(context, "Error: ${e.message.toString()}", Toast.LENGTH_LONG).show()
            }

        }
    }

    fun getFavoriteUsers(): LiveData<ArrayList<User>> = listFavoriteUsers

}