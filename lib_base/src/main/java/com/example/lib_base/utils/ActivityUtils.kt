package com.example.lib_base.utils

import android.app.Activity
import android.content.Intent
import androidx.annotation.AnimRes

/***
 * activity界面跳转
 */
fun <T:Activity>Activity.navigateTo(destination:Class<T>, @AnimRes enter:Int=0,@AnimRes exit:Int=0){
    val intent=Intent(this,destination)
    startActivity(intent)
    overridePendingTransition(enter,exit)
}