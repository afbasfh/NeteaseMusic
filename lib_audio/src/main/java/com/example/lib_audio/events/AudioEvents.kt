package com.example.lib_audio.events

import com.example.lib_audio.player.AudioController
import com.example.lib_base.bmob.Song

/**
 * 密封类
 *  不能在外部继承这个类，只能在声明这个类的文件中 子类化这个类
 *  优点：安全
 *       如果一个类的子类是有限的，就需要使用密封类
 *
 *       Car -> 无限
 *       NetworkStatus -> 成功 失败
 *  如果子类没有任何属性 可以定义为Object类
 */
sealed class AudioEvents
//音频开始加载的事件
class AudioLoadEvent(val music:Song):AudioEvents()
//开始播放事件
object AudioStartEvent : AudioEvents()
//暂停播放事件
object AudioPauseEvent: AudioEvents()
//进度变化事件
class AudioProgressChangeEvent(val now:Int,val total:Int):AudioEvents()
//播放模式改变事件
class AudioPlayModeChangeEvent(val mode: AudioController.PlayMode):AudioEvents()
//音乐收藏和取消收藏
class AudioFavoriteStatusChangeEvent(val music: Song, val isFavorite:Boolean):AudioEvents()