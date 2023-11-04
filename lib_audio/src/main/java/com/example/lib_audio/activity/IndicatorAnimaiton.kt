package com.example.lib_audio.activity

import android.view.View
import android.view.animation.AnimationUtils
import com.example.lib_audio.R

object IndicatorAnimaiton {
    fun startAnimation(target:View){
        AnimationUtils.loadAnimation(target.context, R.anim.indicator_rotate_anim_start).apply {
            fillAfter=true
            target.startAnimation(this)
        }
    }
    fun stopAnimation(target:View){
        AnimationUtils.loadAnimation(target.context, R.anim.indicator_rotate_anim_stop).apply {
            fillAfter=true
            target.startAnimation(this)
        }
    }
}