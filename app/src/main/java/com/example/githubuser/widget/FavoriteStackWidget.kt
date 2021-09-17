package com.example.githubuser.widget

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import android.widget.Toast
import androidx.core.net.toUri
import com.example.githubuser.R

class FavoriteStackWidget : AppWidgetProvider() {

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        super.onReceive(context, intent)
        if (intent.action != null) {
            if (intent.action == INTENT_ACTION) {
                val username = intent.getStringExtra(EXTRA_ITEM)
                Toast.makeText(context, "$username", Toast.LENGTH_SHORT).show()
            }
        }
    }

    companion object {

        private const val INTENT_ACTION = "com.example.githubuser.INTENT_ACTION"
        const val EXTRA_ITEM = "com.example.githubuser.EXTRA_ITEM"

        private fun updateAppWidget(context: Context, appWidgetManager: AppWidgetManager, appWidgetId: Int) {
            val intent = Intent(context, StackWidgetService::class.java)
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val views = RemoteViews(context.packageName, R.layout.favorite_stack_widget)
            views.setRemoteAdapter(R.id.stack_view, intent)
            views.setEmptyView(R.id.stack_view, R.id.empty_view)

            val actionIntent = Intent(context, FavoriteStackWidget::class.java)
            actionIntent.action = INTENT_ACTION
            actionIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, appWidgetId)
            intent.data = intent.toUri(Intent.URI_INTENT_SCHEME).toUri()

            val toastPendingIntent = PendingIntent.getBroadcast(context, 0, actionIntent, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setPendingIntentTemplate(R.id.stack_view, toastPendingIntent)

            appWidgetManager.apply {
                notifyAppWidgetViewDataChanged(appWidgetId, R.layout.widget_item)
                updateAppWidget(appWidgetId, views)
            }
        }
    }
}