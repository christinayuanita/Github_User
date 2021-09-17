package com.example.githubuser.adapter

import android.content.Context
import android.content.Intent
import android.os.Binder
import android.widget.RemoteViews
import android.widget.RemoteViewsService
import androidx.core.os.bundleOf
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.example.githubuser.R
import com.example.githubuser.database.DatabaseContract.FavoriteUserColumns.Companion.CONTENT_URI
import com.example.githubuser.database.DatabaseContract.FavoriteUserColumns.Companion.USERNAME
import com.example.githubuser.helper.MappingHelper
import com.example.githubuser.models.User
import com.example.githubuser.widget.FavoriteStackWidget

class StackRemoteViewsFactory(private val mContext: Context) : RemoteViewsService.RemoteViewsFactory {

    private val mWidgetItems = ArrayList<User>()

    override fun onDataSetChanged() {
        val identityToken = Binder.clearCallingIdentity()
        val cursor = mContext.contentResolver.query(
            CONTENT_URI,
            null,
            null,
            null,
            "$USERNAME ASC")
        if (cursor != null) {
            val users = MappingHelper.mapCursorToArrayList(cursor)
            mWidgetItems.apply {
                clear()
                addAll(users)
            }
            cursor.close()
        }
        Binder.restoreCallingIdentity(identityToken)
    }

    override fun onCreate() {

    }

    override fun onDestroy() {

    }

    override fun getCount(): Int = mWidgetItems.size

    override fun getViewAt(position: Int): RemoteViews {
        val rv = RemoteViews(mContext.packageName, R.layout.widget_item)
        val bitmap = Glide.with(mContext)
            .asBitmap()
            .load(mWidgetItems[position].avatarUrl)
            .apply(RequestOptions().centerCrop())
            .submit()
            .get()
        rv.apply {
            setImageViewBitmap(R.id.iv_avatar_widget, bitmap)
            setTextViewText(R.id.tv_username_widget, mWidgetItems[position].login)
        }
        val extras = bundleOf(
            FavoriteStackWidget.EXTRA_ITEM to mWidgetItems[position].login
        )
        val fillInIntent = Intent()
        fillInIntent.putExtras(extras)
        rv.setOnClickFillInIntent(R.id.iv_avatar_widget, fillInIntent)
        return rv
    }

    override fun getLoadingView(): RemoteViews? = null

    override fun getViewTypeCount(): Int  = 1

    override fun getItemId(position: Int): Long  = 0

    override fun hasStableIds(): Boolean = false
}