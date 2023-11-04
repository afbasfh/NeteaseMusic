package com.example.lib_base.bmob

import cn.bmob.v3.datatype.BmobPointer

class MusicManager private constructor(){
    private var mMusics = emptyList<Song>()
    companion object{
        val instance=MusicManager()
    }

    fun getMusic()=mMusics
    /***
     * 加载音乐
     */
    fun loadMusics(onStart:()->Unit,callback:(List<Song>)->Unit={}){
        BmobManager.getInstance().queryMusics(
            onStart = {onStart},
            onEnd = {songs->
                songs?.let {
                    mMusics=it
                    callback(songs)
                    return@queryMusics
                }
             callback(emptyList())
            })
    }
}