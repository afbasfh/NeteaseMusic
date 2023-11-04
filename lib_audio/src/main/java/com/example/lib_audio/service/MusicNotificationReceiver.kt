package com.example.lib_audio.service

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.lib_audio.player.AudioController

class MusicNotificationReceiver:BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val action = intent!!.getStringExtra(NotificationHelper.BROADCAST_EXTRA_KEY)
        when(action){
            NotificationHelper.ACTION_PLAY_PAUSE ->{
                AudioController.instance.playOrPause()}
            NotificationHelper.ACTION_NEXT ->{
                AudioController.instance.playNext()}
            NotificationHelper.ACTION_PREVIOUS ->{
                AudioController.instance.playPrevious()}
        }
    }
}