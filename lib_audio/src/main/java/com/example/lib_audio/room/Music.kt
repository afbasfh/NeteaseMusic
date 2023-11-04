package com.example.lib_audio.room

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * 数据库中音乐表信息
 */
@Entity
data class Music(
    val musicId:String ,//音乐的objectId
    @PrimaryKey(autoGenerate = true)
    val id:Int = 0
)