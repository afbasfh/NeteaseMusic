package com.example.lib_audio.player

import android.content.Context
import java.lang.ref.WeakReference

/**
 * 程序创建时 就提供一个context对象 applicationContext
 */
class AudioHelper {
    lateinit var context:Context
    companion object{
        val instance = AudioHelper()
    }

    /**
     * 初始化一个context
     */
    fun init(context: Context){
        this.context = context
    }
}