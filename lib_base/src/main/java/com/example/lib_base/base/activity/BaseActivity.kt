package com.example.lib_base.base.activity

import android.app.StatusBarManager
import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.LayoutInflater
import androidx.appcompat.app.AppCompatActivity
import androidx.viewbinding.ViewBinding
import com.jaeger.library.StatusBarUtil


abstract class BaseActivity:AppCompatActivity() {
    private var _binding: ViewBinding?=null
    val binding: ViewBinding?
        get()=_binding

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)

        val resId=getLayoutId()
        if (resId!=0)
            setContentView(resId)
        _binding=getLayoutBinding()
        if (_binding!=null)
            setContentView(_binding!!.root)

        StatusBarUtil.setTransparent(this)
        initUi()
    }
    open fun initUi(){}

    /***
     * 获取布局的资源Id
     */
    open fun getLayoutId():Int=0
    /***
     * 获取绑定类对象
     */
    open fun getLayoutBinding():ViewBinding?=null

    fun navigateToActivity(activityKClass:Class<*>,enter:Int,exit:Int){
        val intent=Intent(this,activityKClass)
        startActivity(intent)
        if (enter!=0&&exit!=0)overridePendingTransition(enter,exit)
    }
}