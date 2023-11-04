package com.example.lib_base.utils

import kotlinx.coroutines.*

/***
 * 倒计时封装
 * 1 倒数 正数
 * 2 随即暂停
 * 3 暂停-》启动
 * 4 回调当前数的数子
 * 5自己设置事件间隔
 */
class CountTimer {
    private  var minterval=1000L
    private var mOrder=Order.DOWN
    private var mCallBack:(CountTimer,Int)->Unit={_,_->}
    private var mOriginalValue=0
    private var job:Job?=null
    /***
     * 设置时间间隔
     */
    fun timeInterval(interval:Long):CountTimer{
        minterval=interval
        return this
    }

    /***
     * 设置顺序
     */
    fun order(order:Order):CountTimer{
        mOrder=order
        return this
    }

    /***
     * 回调数据
     */
    fun setCallBack(callback:(CountTimer,Int)->Unit={_,_->}):CountTimer{
        mCallBack=callback
        return this
    }

    fun startValue(value:Int):CountTimer{
        mOriginalValue=value
        return this
    }

    fun build():CountTimer{
        return this
    }

    /***
     * 开启定时
     */
    fun start():CountTimer{
        job=CoroutineScope(Dispatchers.IO).launch {
            while (isActive){
                delay(minterval)
                if (mOrder!=Order.NONE){
                if (mOrder==Order.DOWN)mOriginalValue--else mOriginalValue++
            }
//                回调数据
                withContext(Dispatchers.Main){
                    mCallBack(this@CountTimer,mOriginalValue)
                }
        }
        }
        return this
    }

    /**
     暂停定时
     */
    fun stop():CountTimer{
        job?.let {
        CoroutineScope(Dispatchers.Main).launch{
            it.cancelAndJoin()
        }}
        return this
    }


    /** 正数还是倒数*/
    enum class Order{
        DOWN,UP,NONE
    }
}