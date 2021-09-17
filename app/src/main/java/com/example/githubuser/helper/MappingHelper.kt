package com.example.githubuser.helper

import android.database.Cursor
import android.provider.BaseColumns._ID
import com.example.githubuser.database.DatabaseContract.FavoriteUserColumns.Companion.AVATAR
import com.example.githubuser.database.DatabaseContract.FavoriteUserColumns.Companion.URL
import com.example.githubuser.database.DatabaseContract.FavoriteUserColumns.Companion.USERNAME
import com.example.githubuser.models.User

object MappingHelper {

    fun mapCursorToArrayList(usersCursor: Cursor?): ArrayList<User> {
        val usersList = ArrayList<User>()
        usersCursor?.apply {
            while (moveToNext()) {
                val id = getInt(getColumnIndexOrThrow(_ID))
                val avatar = getString(getColumnIndexOrThrow(AVATAR))
                val username = getString(getColumnIndexOrThrow(USERNAME))
                val url = getString(getColumnIndexOrThrow(URL))
                usersList.add(User(id, username, url, avatar))
            }
        }
        return usersList
    }
}