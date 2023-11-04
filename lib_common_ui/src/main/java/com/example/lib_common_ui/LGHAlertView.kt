package com.example.lib_common_ui

import android.content.Context
import android.graphics.PixelFormat
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.Animation
import android.widget.FrameLayout
import androidx.fragment.app.Fragment

/***
 * 做一个通用的提示试图
 * 1.需要传递视图对于的view或者ResID
 * 2.随时随地使用
 *
 * 在窗口上面添加到自己的view
 */
class LGHAlertView {
    private lateinit var mContext: Context
    private lateinit var mView:View
    private lateinit var mWindowManager:WindowManager
    private lateinit var rootContainer:FrameLayout
    constructor(context: Context,resId:Int){
        mContext=context
        mView= LayoutInflater.from(context).inflate(resId, null)
        mWindowManager =mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        rootContainer= FrameLayout(mContext)

    }
    constructor(context: Context,view: View){
        mContext=context
        mView=view
        mWindowManager =mContext.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        rootContainer= FrameLayout(mContext)
    }
    //    解析出resId对应的view
    //    获取窗口的管理器
    fun show(position:Positon=Positon.CENTER,autoDismiss:Boolean=true) {
//        这个控件的布局参数
        val Ip = WindowManager.LayoutParams(
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION_PANEL,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            PixelFormat.TRANSLUCENT
        )
        Ip.gravity = when (position) {
            Positon.TOP -> Gravity.CENTER_HORIZONTAL or Gravity.TOP
            Positon.CENTER -> Gravity.CENTER_HORIZONTAL or Gravity.CENTER_VERTICAL
            Positon.BOTTON -> Gravity.CENTER_HORIZONTAL or Gravity.BOTTOM
        }
        rootContainer.addView(mView)
        mWindowManager.addView(rootContainer, Ip)
        mView.startAnimation(AnimUtils.SlideInFromTopAnimation())

        if (autoDismiss == true) {
            rootContainer.postDelayed({
                mView.startAnimation((AnimUtils.SlideOutFromTopAnimation {
                    dismiss()
                }))
            }, 2000)
        }
    }



    fun dismiss() {
        if (rootContainer.parent != null) {
            mWindowManager.removeView(rootContainer)
        }
    }

    enum class Positon {
        CENTER, TOP, BOTTON
    }
}