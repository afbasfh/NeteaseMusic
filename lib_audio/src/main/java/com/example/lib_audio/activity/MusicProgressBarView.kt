package com.example.lib_audio.activity

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.example.lib_audio.player.AudioController
import com.google.android.material.internal.ViewUtils

class MusicProgressBarView(context: Context, attrs: AttributeSet?): View(context,attrs) {
    private val mPaint = Paint().apply {
        color = Color.GRAY
        style = Paint.Style.FILL
        isAntiAlias = true
    }
    private var mRight:Float = 0f

    @SuppressLint("RestrictedApi")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        var mWidth = MeasureSpec.getSize(widthMeasureSpec)
        var mHeight = MeasureSpec.getSize(heightMeasureSpec)

        mWidth = when (MeasureSpec.getMode(widthMeasureSpec)){
            MeasureSpec.EXACTLY -> mWidth
            else -> ViewUtils.dpToPx(context, 100).toInt()
        }
        mHeight = when (MeasureSpec.getMode(heightMeasureSpec)){
            MeasureSpec.EXACTLY -> mHeight
            else -> ViewUtils.dpToPx(context, 4).toInt()
        }

        setMeasuredDimension(mWidth,mHeight)
    }

    override fun onDraw(canvas: Canvas?) {
        //绘制背景
        mPaint.color = Color.GRAY
        canvas?.drawRoundRect(0f,0f,width.toFloat(),height.toFloat(),height.toFloat()/2,height.toFloat()/2,mPaint)
        //绘制进度
        mPaint.color = Color.WHITE
        canvas?.drawRoundRect(0f,0f,mRight,height.toFloat(),height.toFloat()/2,height.toFloat()/2,mPaint)
    }

    fun changeProgress(progress:Float){
        mRight = progress * width
        invalidate()
    }

    fun reset(){
        mRight = 0f
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when(event?.action){
            MotionEvent.ACTION_DOWN ->{
                mRight = event.x
                invalidate()
                AudioController.instance.pause()
            }
            MotionEvent.ACTION_MOVE ->{
                mRight = event.x
                invalidate()
            }
            MotionEvent.ACTION_UP ->{
                val rate = event.x / width
                AudioController.instance.play()
                AudioController.instance.seekTo(rate)
            }
        }
        return true
    }

}