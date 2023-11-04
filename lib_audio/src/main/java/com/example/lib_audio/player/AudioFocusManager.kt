package com.example.lib_audio.player

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.AudioManager.OnAudioFocusChangeListener
import android.os.Build

/**
 * 管理音频焦点
 *  1. 请求焦点
 *      AUDIOFOCUS_GAIN 获取焦点
 *  2. 释放焦点
 */
class AudioFocusManager(val listener:AudioFocusRequestChangeListener): OnAudioFocusChangeListener {

    private var mAudioManager:AudioManager //系统管理音频焦点的对象
    private val mAudioFocusRequest:AudioFocusRequest? by lazy {
        var temp:AudioFocusRequest? = null
        if (Build.VERSION.SDK_INT >= 26) {
            val audioAttr = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_MEDIA) //配置类型为 音频/视频
                .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC) // 配置具体内容为 音乐
                .build()
            temp = AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN)
                .setAudioAttributes(audioAttr)
                .setOnAudioFocusChangeListener(this) //焦点发生变化的回调事件
                .build()
        }
        temp
    }
    init {
        //获取系统管理音频的管理器
        mAudioManager = AudioHelper.instance.context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    /**
     * 请求焦点
     */
    fun requestAudioFocus(){
        if (Build.VERSION.SDK_INT >= 26) {
            mAudioManager.requestAudioFocus(mAudioFocusRequest!!)
        }else{
            mAudioManager.requestAudioFocus(this,AudioManager.STREAM_MUSIC,AudioManager.AUDIOFOCUS_GAIN)
        }
    }

    /**
     * 释放焦点
     */
    fun abandonAudioFocus(){
        if (Build.VERSION.SDK_INT >= 26) {
            mAudioManager.abandonAudioFocusRequest(mAudioFocusRequest!!)
        }else{
            mAudioManager.abandonAudioFocus(this)
        }
    }

    /**
     *  AUDIOFOCUS_GAIN 重新获取到音频焦点
     *  AUDIOFOCUS_LOSS 永久失去音频焦点    使用腾讯视频看视频去了
     *  AUDIOFOCUS_LOSS_TRANSIENT 短暂性失去焦点 可以恢复   电话
     *  AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK 瞬间失去焦点   其他通知铃声想起
     */
    override fun onAudioFocusChange(focusChange: Int) {
        when(focusChange){
            AudioManager.AUDIOFOCUS_GAIN -> listener.onAudioFocusGain()
            AudioManager.AUDIOFOCUS_LOSS -> listener.onAudioFocusLoss()
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT -> listener.onAudioFocusLossTransient()
            AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK -> listener.onAudioFocusLossTransientDuck()
        }
    }

    interface AudioFocusRequestChangeListener{
        fun onAudioFocusGain()
        fun onAudioFocusLoss()
        fun onAudioFocusLossTransient()
        fun onAudioFocusLossTransientDuck()
    }

}