package com.example.lib_audio.service

import android.app.Service
import android.content.Intent
import android.content.IntentFilter
import android.os.Build
import android.os.IBinder
import com.example.lib_audio.activity.TimerUtils
import com.example.lib_audio.events.AudioLoadEvent
import com.example.lib_audio.events.AudioPauseEvent
import com.example.lib_audio.events.AudioProgressChangeEvent
import com.example.lib_audio.events.AudioStartEvent
import com.example.lib_audio.player.AudioController
import com.example.lib_audio.player.AudioHelper
import com.example.lib_base.bmob.Song
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MusicService: Service() {
    private var mMusic:Song?=null
    private val mNotificationHelper=NotificationHelper()
    private val mMusicNotificationReceiver = MusicNotificationReceiver()
    companion object{
        val MUSIC_EXTRA_KEY = "music_extra_key"
        /**
         * 提供给外部快速启动一个音乐服务
         */
        fun startMusicService(music: Song){
            val serviceIntent=Intent(AudioHelper.instance.context,MusicService::class.java)
            serviceIntent.putExtra(MUSIC_EXTRA_KEY,music)
            AudioHelper.instance.context.startService(serviceIntent)
        }
    }
    override fun onCreate() {
        super.onCreate()
        EventBus.getDefault().register(this)
//        动态注册通过
        registerReceiver()
    }
    private fun registerReceiver(){
        val intentFilter = IntentFilter(NotificationHelper.ACTION_BROADCAST_NOTIFICATION)
        AudioHelper.instance.context.registerReceiver(mMusicNotificationReceiver,intentFilter)
    }
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        //获取需要播放的音乐
        mMusic = intent!!.getSerializableExtra(MUSIC_EXTRA_KEY) as Song

        //完成加载所需的操作
        play()
        return super.onStartCommand(intent, flags, startId)
    }

    private fun play(){
        mMusic?.let { AudioController.instance.playMusic(it) }
    }
    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
        AudioHelper.instance.context.unregisterReceiver(mMusicNotificationReceiver)
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoadEvent(event: AudioLoadEvent){
        mNotificationHelper.init(event.music)
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onStartEvent(event: AudioStartEvent){
        mNotificationHelper.showStart()
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPauseEvent(event: AudioPauseEvent){
        mNotificationHelper.showPause()

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onProgressChangeEvent(event: AudioProgressChangeEvent){

    }
}
