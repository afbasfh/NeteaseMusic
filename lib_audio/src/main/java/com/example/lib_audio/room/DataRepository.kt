package com.example.lib_audio.room

import androidx.lifecycle.LiveData
import androidx.room.Room
import com.example.lib_audio.player.AudioHelper
import com.example.lib_base.bmob.Song
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class DataRepository {
    //数据库对象
    private val database: MusicDatabase by lazy {
        Room.databaseBuilder(
            AudioHelper.instance.context,
            MusicDatabase::class.java,
            "musicdb"
        ).build()
    }
//    收藏的说有数据
    private val musicLiveData: LiveData<List<Music>> = database.getMusicDao().queryMusic()

    //协程对象
    private val scope = CoroutineScope(Dispatchers.IO)
    companion object{
        val instance = DataRepository()
    }

    //收藏一首歌曲
    fun addMusic(musicID:String){
        scope.launch {
            database.getMusicDao().addMusic(Music(musicID))
        }
    }

    //取消收藏一首歌曲
    fun deleteMusic(musicID:String){
        scope.launch {
            database.getMusicDao().deleteMusic(musicID)
        }
    }

    //查询音乐是否被收藏

    fun checkIsFavorite(song: Song, callback:(Boolean)->Unit){
        scope.launch {
            val results = database.getMusicDao().queryMusic(song.objectId)
            withContext(Dispatchers.Main){
                callback(results.isNotEmpty())
            }
        }
    }
}




