package com.example.neteasemusic.app

import android.app.Application
import cn.bmob.v3.Bmob
import com.example.lib_audio.player.AudioHelper


class MyApplication:Application() {
    override fun onCreate() {
        super.onCreate()
        Bmob.initialize(applicationContext,"cebafbcbed8ac1136beddbed2a7f348c")
        AudioHelper.instance.init(this)
    }
}