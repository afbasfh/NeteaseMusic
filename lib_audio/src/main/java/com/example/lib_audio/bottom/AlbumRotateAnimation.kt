package com.example.lib_audio.bottom

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.View
import android.view.animation.LinearInterpolator

/**
 * 管理封面图片的旋转动画
 * 开始
 * 暂停
 * 恢复
 * 取消
 */
class AlbumRotateAnimation(val target: View) {
    private  var mAnimation:ObjectAnimator?=null

    fun start(){
        if (mAnimation!=null&&mAnimation!!.isPaused){
            resume()
            return
        }
        if (mAnimation!=null&&mAnimation!!.isStarted){
            release()
        }
        mAnimation = ObjectAnimator.ofFloat(target,"rotation",360f).apply {
            duration=5000
            repeatMode=ValueAnimator.RESTART
            repeatCount=ValueAnimator.INFINITE
            interpolator=LinearInterpolator()
            start()
        }
    }

    fun reset(){
        release()
    }
    fun pause(){
        mAnimation?.pause()
    }

    fun resume(){
        mAnimation?.resume()
    }

    fun release(){
        mAnimation?.cancel()
        mAnimation=null
    }
}