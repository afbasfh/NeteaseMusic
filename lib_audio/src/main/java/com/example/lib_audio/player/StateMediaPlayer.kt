package com.example.lib_audio.player

import android.media.MediaPlayer
import android.media.MediaPlayer.OnCompletionListener

/**
 * 有状态的MediaPlayer
 * 空闲IDLE 初始状态INITIATE 播放状态START 暂停状态PAUSE  释放状态RELEASE 结束状态COMPLETION
 */
class StateMediaPlayer: MediaPlayer(),OnCompletionListener{
    private var mStatus = Status.IDLE

    init {
        //配置播放结束的监听器，结束之后回调给当前对象
        setOnCompletionListener(this)
    }

    /**
     * 获取当前状态
     */
    fun getStatus() = mStatus

    override fun reset() { //空闲
        super.reset()
        mStatus = Status.IDLE
    }

    override fun setDataSource(path: String?) { //初始化
        super.setDataSource(path)
        mStatus = Status.INITIATE
    }

    override fun start() { //播放
        super.start()
        mStatus = Status.START
    }

    override fun pause() { //暂停
        super.pause()
        mStatus = Status.PAUSE
    }

    override fun onCompletion(mp: MediaPlayer?) { //结束
        mStatus = Status.COMPLETION
    }
    override fun release() { //释放
        super.release()
        mStatus = Status.RELEASE
    }

    enum class Status{
        IDLE,INITIATE,START,PAUSE,RELEASE,COMPLETION
    }
}