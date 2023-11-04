package com.example.lib_common_ui

import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationSet
import android.view.animation.BounceInterpolator
import android.view.animation.TranslateAnimation
import com.google.android.material.internal.ViewUtils.dpToPx

object AnimUtils {
    /***
     * 左右摆动的动画
     */
    @SuppressLint("RestrictedApi")
    fun startSwingAnimation(target: View, scope: Int = 20) {
        val pxScope = dpToPx(target.context, 20)
        ObjectAnimator.ofFloat(target, "translationX", 0f, -pxScope, pxScope, 0f).apply {
            duration = 200
            repeatCount = 3
            start()
        }
    }

    /***
     * 从顶部进入动画
     */
    fun SlideInFromTopAnimation(): Animation {
        return TranslateAnimation(
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            -1f,
            Animation.RELATIVE_TO_SELF,
            0f
        ).apply {
            duration = 700
            interpolator = BounceInterpolator()
        }
    }

    fun SlideOutFromTopAnimation(onEnd:()->Unit={}): Animation {
        return TranslateAnimation(
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            0f,
            Animation.RELATIVE_TO_SELF,
            -1f
        ).apply {
            duration = 200
            setAnimationListener(object :Animation.AnimationListener{
                override fun onAnimationStart(animation: Animation?) {

                }

                override fun onAnimationEnd(animation: Animation?) {
                    onEnd()
                }

                override fun onAnimationRepeat(animation: Animation?) {

                }
            })
        }
    }

    fun alertAutoOutAnimation(): Animation {
        return AnimationSet(false).apply {
            addAnimation(SlideInFromTopAnimation())
            addAnimation(SlideOutFromTopAnimation())
        }
    }
}