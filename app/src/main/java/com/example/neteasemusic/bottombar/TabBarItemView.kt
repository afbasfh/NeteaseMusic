package com.example.neteasemusic.bottombar

import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import com.example.neteasemusic.databinding.LayoutLabberItemBinding

class TabBarItemView(
    private val context: Context,
    private val tabBarItem: TabBarItem,
    private val callback: (Int) -> Unit
){
    private val mBinding: LayoutLabberItemBinding = LayoutLabberItemBinding.inflate(LayoutInflater.from(context))
    private val redColor = Color.parseColor("#F12A26")
    private val defaultColor = Color.GRAY
    val mView = mBinding.root

    init {
        mBinding.root.setOnClickListener {
            if (tabBarItem.selected) return@setOnClickListener
            callback(tabBarItem.index)
            select()
            tabBarItem.selected = true
        }
        mBinding.ivIcon.setImageResource(tabBarItem.normalRes)
        mBinding.tvTitle.text = tabBarItem.title
    }

    fun select(){
        startScaleAnimation(mBinding.ivIcon,0.4f,1f,300)
        mBinding.ivIcon.setImageResource(tabBarItem.selectRes)
        mBinding.tvTitle.setTextColor(redColor)
        tabBarItem.selected = true
    }

    fun deSelect(){
        mBinding.ivIcon.setImageResource(tabBarItem.normalRes)
        mBinding.tvTitle.setTextColor(defaultColor)
        tabBarItem.selected = false
    }

    fun startScaleAnimation(target: View, fromValue:Float, toValue: Float, mDuration:Long = 500) {
        val xAnim = ObjectAnimator.ofFloat(target,"scaleX",fromValue,toValue)
        val yAnim = ObjectAnimator.ofFloat(target,"scaleY",fromValue,toValue)
        AnimatorSet().apply {
            playTogether(xAnim,yAnim)
            duration = mDuration
            start()
        }
    }
}