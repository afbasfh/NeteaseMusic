package com.example.lib_audio.bottom

import android.view.View
import android.widget.ImageView
import com.bumptech.glide.Glide

object ImageLoader {
    /***
     * 加载图片
     */
    fun loadCircleImage(url:String,target:ImageView){
        Glide.with(target.context)
            .load(url)
            .circleCrop()
            .into(target)
    }
}