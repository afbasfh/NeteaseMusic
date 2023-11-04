package com.example.lib_audio.player


import com.example.lib_audio.events.*
import com.example.lib_base.bmob.Song
import com.example.lib_base.utils.CountTimer
import org.greenrobot.eventbus.EventBus


/**
 * 1.管理音乐相关操作
 * 2.监听焦点事件
 */
class AudioPlayer: AudioFocusManager.AudioFocusRequestChangeListener {
    private  val player = StateMediaPlayer()  //真正完成播放的对象
    private var timer: CountTimer? = null //定时器 返回播放进度
    private val mAudioFocusManager = AudioFocusManager(this)
    /**
     * 加载音乐
     */
    fun loadMusic(music: Song){
        player.reset() //重置
        player.setDataSource(music.url)
        player.prepareAsync()
        player.setOnPreparedListener {
            start()
        }
//        music.duration = totalTime()
        //发送音乐加载完毕事件
        music.duration=totalTime()
        EventBus.getDefault().post(AudioLoadEvent(music))
    }

        fun seekMusicTo(rate:Float){
            player.seekTo((rate*totalTime()).toInt())
        }

    //音乐总时长
    private fun totalTime() = player.duration

    //获取当前播放位置
    private fun currentTime() = player.currentPosition

    private fun initTimer(){
        timer = CountTimer()
        timer?.order(CountTimer.Order.NONE) //不需要计时
        timer?.timeInterval(100)
        timer?.start()
        timer?.setCallBack() { _ , _ ->
            //发送音乐播放进度的事件
            EventBus.getDefault().post(AudioProgressChangeEvent(currentTime(),totalTime()))
        }
    }
    //播放音乐
    fun start(){
        player.start() //播放音乐
        initTimer() //更新进度
//        发送音乐开始播放的事件
        EventBus.getDefault().post(AudioStartEvent)
    }
    //暂停播放
    fun pause(){
        player.pause()
        timer?.stop()
        timer = null
        //发送音乐暂停播放的事件
        EventBus.getDefault().post(AudioPauseEvent)
    }

    //恢复播放
    fun resume(){
        start()
    }

    //释放 程序退出时
    fun release(){
        player.release()
        timer?.stop()
        timer = null
        //发送Release的事件
    }

    override fun onAudioFocusGain() {
        //音量恢复正常
        player.setVolume(1.0f,1.0f)
        if (player.getStatus() == StateMediaPlayer.Status.PAUSE){
            //如果之前短暂性丢失导致音频暂停，此刻需要继续播放
            resume()
        }
    }

    /**
     * 是否在播放
     */
    fun isPlaying() = player.getStatus() == StateMediaPlayer.Status.START
    override fun onAudioFocusLoss() {
        release()
    }

    override fun onAudioFocusLossTransient() {
        pause()
    }

    override fun onAudioFocusLossTransientDuck() {
        //将音量调小
        player.setVolume(0.5f,0.5f)
    }

}