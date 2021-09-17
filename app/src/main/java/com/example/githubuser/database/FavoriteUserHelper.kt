package com.example.githubuser.database

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.SQLException
import android.database.sqlite.SQLiteDatabase
import com.example.githubuser.database.DatabaseContract.FavoriteUserColumns.Companion.TABLE_NAME
import com.example.githubuser.database.DatabaseContract.FavoriteUserColumns.Companion.USERNAME

class FavoriteUserHelper(context: Context) {

    private var databaseHelper: DatabaseHelper = DatabaseHelper(context)
    private lateinit var database: SQLiteDatabase

    companion object{
        private const val DATABASE_TABLE = TABLE_NAME
        private val INSTANCE: FavoriteUserHelper? = null
        fun getInstance(context: Context) : FavoriteUserHelper =
            INSTANCE ?: synchronized(this){
                INSTANCE ?: FavoriteUserHelper(context)
            }
    }

    @Throws(SQLException::class)
    fun open(){
        database = databaseHelper.writableDatabase
    }

    fun queryAll(): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            null,
            null,
            null,
            null,
            "$USERNAME ASC")
    }

    fun queryByUsername(username: String): Cursor {
        return database.query(
            DATABASE_TABLE,
            null,
            "$USERNAME = ?",
            arrayOf(username),
            null,
            null,
            null)
    }

    fun insert(values: ContentValues?): Long = database.insert(DATABASE_TABLE, null, values)

    fun deleteByUsername(username: String): Int =
        database.delete(DATABASE_TABLE,"$USERNAME = '$username'", null)

}