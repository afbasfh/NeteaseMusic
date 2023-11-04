package com.example.lib_audio.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.widget.RemoteViews
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.NotificationTarget
import com.example.lib_audio.R
import com.example.lib_audio.player.AudioHelper
import com.example.lib_base.bmob.Song
import okhttp3.internal.notify

/**
 * 管理通知
 *  0.创建Notification
 *  1.外部播放暂停下一首上一首 事件来了该干什么
 *  2.自己视图布局长什么样
 *  3.自己的事件如何传出去
 */
class NotificationHelper {
    private lateinit var mNotification: Notification
    private lateinit var mNotificationManager: NotificationManager
    private lateinit var mMusic: Song
    private lateinit var mSmallRemoteView: RemoteViews //小布局
    private lateinit var mBigRemoteView: RemoteViews //大布局

    companion object {
        val CHANNEL_ID = "music_channel_id"
        val CHANNEL_NAME = "music_channel_name"
        val NOTIFICATION_ID = "music_notification_id"
        val ACTION_BROADCAST_NOTIFICATION ="action_broadcast_notification"
        val BROADCAST_EXTRA_KEY = "broadcast_extra_key"
        val ACTION_PLAY_PAUSE = "action_play_pause"
        val ACTION_NEXT ="actioin_next"
        val ACTION_PREVIOUS = "action_previous"
    }

    fun init(music: Song) {
        //创建通知管理器
        mNotificationManager = AudioHelper.instance.context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        //保存当前音乐
        mMusic = music
        initNotification()
    }

    /**
     * 创建Notification
     */
    private fun initNotification() {
        //0.创建自定义大小布局视图
        initRemoteViews()
//     1. channel
        if (Build.VERSION.SDK_INT >= 26) {
            val channel =   NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_LOW)
            channel.enableLights(false) //true 有通知时 手机会闪烁
            channel.enableVibration(false)// true 有通知时 会震动
            mNotificationManager.createNotificationChannel(channel)
        }
//        2.创建Notication
        mNotification = NotificationCompat
            .Builder(AudioHelper.instance.context, CHANNEL_ID)
            .setSmallIcon(R.drawable.small_icon)
            .setContent(mSmallRemoteView)
            .build()
//        loadImageForRemoteView(R.id.albumView,mSmallRemoteView, mNotification,1)
//        loadImageForRemoteView(R.id.albumView,mBigRemoteView, mNotification,1)
        mNotificationManager.notify(1,mNotification)
    }
    private fun initRemoteViews(){
        val packageName = AudioHelper.instance.context.packageName
        mSmallRemoteView = RemoteViews(packageName,R.layout.layout_notification_collpase).apply {
            setTextViewText(R.id.titleView,mMusic.title)//歌名
            setTextViewText(R.id.authorView,mMusic.author)//歌手
            setImageViewResource(R.id.previous,R.drawable.notification_previous)
            setImageViewResource(R.id.playorpause,R.drawable.notification_play)
            setImageViewResource(R.id.next,R.drawable.notification_next)
        }
        mBigRemoteView = RemoteViews(packageName,R.layout.layout_notification_expanded).apply {
            setTextViewText(R.id.titleView,mMusic.title)//歌名
            setTextViewText(R.id.authorView,mMusic.author)//歌手
            setImageViewResource(R.id.previous,R.drawable.notification_previous)
            setImageViewResource(R.id.playorpause,R.drawable.notification_play)
            setImageViewResource(R.id.next,R.drawable.notification_next)
        }
        //配置事件绑定 PendingIntent
        //播放和暂停事件
        val play = Intent(ACTION_BROADCAST_NOTIFICATION)
        play.putExtra(BROADCAST_EXTRA_KEY, ACTION_PLAY_PAUSE)
        val playIntent = PendingIntent.getBroadcast(
            AudioHelper.instance.context.applicationContext,
            1,
            play,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        mSmallRemoteView.setOnClickPendingIntent(R.id.playorpause,playIntent)
        mBigRemoteView.setOnClickPendingIntent(R.id.playorpause,playIntent)

        //next
        val next = Intent(ACTION_BROADCAST_NOTIFICATION)
        next.putExtra(BROADCAST_EXTRA_KEY, ACTION_NEXT)
        val nextIntent = PendingIntent.getBroadcast(
            AudioHelper.instance.context.applicationContext,
            2,
            next,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        mSmallRemoteView.setOnClickPendingIntent(R.id.next,nextIntent)
        mBigRemoteView.setOnClickPendingIntent(R.id.next,nextIntent)
        //上一曲
        val previous = Intent(ACTION_BROADCAST_NOTIFICATION)
        previous.putExtra(BROADCAST_EXTRA_KEY, ACTION_PREVIOUS)
        val previousIntent = PendingIntent.getBroadcast(
            AudioHelper.instance.context.applicationContext,
            3,
            previous,
            PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)
        mSmallRemoteView.setOnClickPendingIntent(R.id.previous,previousIntent)
        mBigRemoteView.setOnClickPendingIntent(R.id.previous,previousIntent)
    }

    /**
     * 给通知上自定义的视图加载图片
     */
    private fun loadImageForRemoteView(viewId:Int, view:RemoteViews, notification: Notification,notificationID: Int){
        val notificationTarget = NotificationTarget(
            AudioHelper.instance.context,
            viewId,
            view,
            notification,
            notificationID
        )
        Glide.with(AudioHelper.instance.context)
            .asBitmap()
            .load(mMusic.album)
            .into(notificationTarget)
    }
    fun showStart(){
        mSmallRemoteView.setImageViewResource(R.id.playorpause,R.drawable.notification_pause)
        mBigRemoteView.setImageViewResource(R.id.playorpause,R.drawable.notification_pause)
        mNotificationManager.notify(1,mNotification)
    }
    fun showPause(){
        mSmallRemoteView.setImageViewResource(R.id.playorpause,R.drawable.notification_play)
        mBigRemoteView.setImageViewResource(R.id.playorpause,R.drawable.notification_play)
        mNotificationManager.notify(1,mNotification)
    }
}