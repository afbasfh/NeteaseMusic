package com.example.lib_audio.activity

object TimerUtils {
    fun stringFromTime(time: Int):String{
        val temp = time / 1000
        //分钟数
        val m = temp / 60
        val s = temp % 60
        val mString = if (m < 10) "0$m" else "$m"
        val sString = if (s < 10) "0$s" else "$s"
        return "$mString:$sString"
    }
}