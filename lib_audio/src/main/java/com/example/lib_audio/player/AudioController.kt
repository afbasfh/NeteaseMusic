package com.example.lib_audio.player


import com.example.lib_audio.events.AudioFavoriteStatusChangeEvent
import com.example.lib_audio.events.AudioPlayModeChangeEvent
import com.example.lib_audio.room.DataRepository
import com.example.lib_base.bmob.Song
import org.greenrobot.eventbus.EventBus
import kotlin.random.Random

/**
 * 上一曲 下一曲 暂停 播放 添加音乐
 */
class AudioController private constructor(){
    private val mAudioPlayer = AudioPlayer() //播放器
    private val mPlayList = arrayListOf<Song>()  //播放队列
    private var mCurrentPlayIndex = 0 //当前播放音乐的索引值
    private var mPlayMode:PlayMode = PlayMode.LOOP //播放模式

    companion object{
        val instance = AudioController()
    }

    /**
     * 添加一首音乐进行播放
     * 1. 没在播放队列 -> 添加 播放
     * 2. 在播放队列 没有播放 -> 播放
     * 3. 在播放队列 正在播放 -> none
     */
    fun playMusic(music:Song){
        if (mPlayList.contains(music)){ //在播放队列
            if (mAudioPlayer.isPlaying()){ //判断是否有歌曲在播放
                if (getNowPlayingMusic() != music){ //判断是否是当前这首歌曲
                    play(music)
                }
            }else{ //没有歌曲在播放
                play(music)
            }
        }else{ //没有在队列中
            mPlayList.add(music)
            play(music)
        }
    }


    /**
     * 播放下一曲
     */
    fun playNext(){
        if (mPlayList.isEmpty()) return
        val nextIndex = when(mPlayMode){
            PlayMode.REPEAT -> mCurrentPlayIndex
            PlayMode.RANDOM -> Random.nextInt(mPlayList.size)
            PlayMode.LOOP ->{
                if (mCurrentPlayIndex + 1 == mPlayList.size){
                    0
                }else{
                    mCurrentPlayIndex+1
                }
            }
        }
        playMusic(mPlayList[nextIndex])
    }

    /**
     * 播放上一曲
     */
    fun playPrevious(){
        if (mPlayList.isEmpty()) return
        val nextIndex = when(mPlayMode){
            PlayMode.REPEAT -> mCurrentPlayIndex
            PlayMode.RANDOM -> Random.nextInt(mPlayList.size)
            PlayMode.LOOP ->{
                if (mCurrentPlayIndex == 0){
                    mPlayList.size - 1
                }else{
                    mCurrentPlayIndex - 1
                }
            }
        }
        playMusic(mPlayList[nextIndex])
    }

    /**
     * 更改播放模式
     */
    fun setPlayMode(mode: PlayMode){
        mPlayMode = mode
    }

    fun pause(){
        mAudioPlayer.pause()
    }

    fun play(){
        mAudioPlayer.start()
    }
    fun playOrPause(){
        if (mAudioPlayer.isPlaying()){
            mAudioPlayer.pause()
        }else{
            mAudioPlayer.resume()
        }
    }

    fun getNowPlayingMusic():Song{
        return mPlayList[mCurrentPlayIndex]
    }

    fun isPlaying() = mAudioPlayer.isPlaying()

    fun seekTo(rate:Float){
        mAudioPlayer.seekMusicTo(rate)
    }
    /**
     * 播放音乐
     */
    private fun play(music:Song){
        mAudioPlayer.loadMusic(music) //播放音乐
        mCurrentPlayIndex = mPlayList.indexOf(music) //改变索引
    }

    /**
     * 更改播放模式
     */
    private val modeList = listOf(PlayMode.LOOP,PlayMode.REPEAT,PlayMode.RANDOM)
    private var mCurrentModeIndex = 0
    fun changePlayMode(){
        if (mCurrentModeIndex + 1 == modeList.size){
            mCurrentModeIndex = 0
        }
        mCurrentModeIndex++
        mPlayMode = modeList[mCurrentModeIndex]
//        推送模式改变的事件
        EventBus.getDefault().post(AudioPlayModeChangeEvent(mPlayMode))
    }

    /**
     * 更改收藏状态
     */
    fun changeFavoriteStatus(){
        //获取当前播放的音乐
        val music = getNowPlayingMusic()
        //判断是否已经收藏
        DataRepository.instance.checkIsFavorite(music){
            if(it){
                DataRepository.instance.deleteMusic(music.objectId)
                //推送音乐收藏或者取消收藏的事件
                EventBus.getDefault().post(AudioFavoriteStatusChangeEvent(music,false))
            }else{
                DataRepository.instance.addMusic(music.objectId)
                EventBus.getDefault().post(AudioFavoriteStatusChangeEvent(music,true))
            }
        }
    }

    enum class PlayMode{
        REPEAT,LOOP,RANDOM
    }

}