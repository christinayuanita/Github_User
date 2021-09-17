package com.example.githubuser.database

import android.net.Uri
import android.provider.BaseColumns

object DatabaseContract {

    const val AUTHORITY = "com.example.githubuser"
    const val SCHEME = "content"

    internal class FavoriteUserColumns : BaseColumns{
        companion object {
            const val TABLE_NAME = "favorite_user"
            const val AVATAR = "avatar_url"
            const val USERNAME = "username"
            const val URL = "html_url"

            val CONTENT_URI: Uri = Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY)
                .appendPath(TABLE_NAME)
                .build()
        }
    }
}