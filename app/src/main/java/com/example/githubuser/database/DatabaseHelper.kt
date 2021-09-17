package com.example.githubuser.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.provider.BaseColumns._ID
import com.example.githubuser.database.DatabaseContract.FavoriteUserColumns.Companion.AVATAR
import com.example.githubuser.database.DatabaseContract.FavoriteUserColumns.Companion.TABLE_NAME
import com.example.githubuser.database.DatabaseContract.FavoriteUserColumns.Companion.URL
import com.example.githubuser.database.DatabaseContract.FavoriteUserColumns.Companion.USERNAME

internal class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "dbfavoriteuser"
        private const val DATABASE_VERSION = 2
        private const val SQL_CREATE_TABLE_FAVORITE_USER = "CREATE TABLE $TABLE_NAME" +
                " ($_ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                " $AVATAR TEXT NOT NULL," +
                " $USERNAME TEXT NOT NULL," +
                " $URL TEXT NOT NULL)"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(SQL_CREATE_TABLE_FAVORITE_USER)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }
}