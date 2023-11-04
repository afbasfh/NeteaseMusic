package com.example.lib_audio.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface MusicDao {
    //增加一首音乐  收藏
    @Insert
     fun addMusic(music: Music)
    //删除一首音乐  取消收藏
    @Query("delete from Music where musicId=:musicID")
    fun deleteMusic(musicID:String)
    //查询
    @Query("select * from Music")
    fun queryMusic(): LiveData<List<Music>>

    //查询musicID对应的歌曲
    @Query("select * from Music where musicId=:musicID")
    fun queryMusic(musicID:String):List<Music>
}