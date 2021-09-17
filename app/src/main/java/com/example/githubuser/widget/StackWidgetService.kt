package com.example.githubuser.widget

import android.content.Intent
import android.widget.RemoteViewsService
import com.example.githubuser.adapter.StackRemoteViewsFactory

class StackWidgetService : RemoteViewsService() {

    override fun onGetViewFactory(intent: Intent?): RemoteViewsFactory = StackRemoteViewsFactory(this.applicationContext)

}