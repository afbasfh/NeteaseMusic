package com.example.lib_audio.bottom

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.lib_audio.R

class ProgressPlayView(context: Context, attrs:AttributeSet?): View(context, attrs) {
    private val playBitmap:Bitmap by lazy {
        BitmapFactory.decodeResource(resources, R.drawable.bottom_play)
    }
    private val stopBitmap:Bitmap by lazy {
        BitmapFactory.decodeResource(resources, R.drawable.bottom_stop)
    }
    private val mCenter:Float by lazy {
        width.toFloat()/2
    }
    private val imageRect:RectF by lazy {
        val size = width.toFloat()/2
        RectF(mCenter-size/2,mCenter-size/2,mCenter+size/2,mCenter+size/2)
    }
    private var mBitmap:Bitmap = playBitmap

    private val mStrokeWith = dp2px(2).toFloat()
    private val circleRadius: Float by lazy {
        (width.toFloat()-2*mStrokeWith)/2
    }
    private val circlePaint = Paint().apply {
        color = Color.parseColor("#55FFFFFF")
        style = Paint.Style.STROKE
        strokeWidth = mStrokeWith
    }
    private var mSweepAngle = 0f
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        var mWidth = MeasureSpec.getSize(widthMeasureSpec)
        var mHeight = MeasureSpec.getSize(heightMeasureSpec)
        val size = Math.min(mWidth,mHeight)
        setMeasuredDimension(size,size)
    }
    override fun onDraw(canvas: Canvas) {
        //绘制暂停和播放图片
        canvas.drawBitmap(mBitmap,null,imageRect,null)

        //绘制默认圆环
        circlePaint.color = Color.parseColor("#55FFFFFF")
        canvas.drawCircle(mCenter,mCenter,circleRadius,circlePaint)

        //绘制进度
        circlePaint.color = Color.parseColor("#FFFFFF")
        canvas.drawArc(
            mStrokeWith,mStrokeWith,width.toFloat()-mStrokeWith,height.toFloat()-mStrokeWith,
            -90f,
            mSweepAngle,
            false,
            circlePaint
        )
    }

    fun View.dp2px(dp:Int) = (context.resources.displayMetrics.density*dp).toInt()

    /**
     * 切换播放状态
     */
    fun showPlay(){
        mBitmap = stopBitmap
        invalidate()
    }

    /**
     * 切换暂停状态
     */
    fun showPause(){
        mBitmap = playBitmap
        invalidate()
    }

    /**
     * 更改进度
     */
    fun changeProgress(progress:Float){
        mSweepAngle = 360 * progress
        invalidate()
    }
}