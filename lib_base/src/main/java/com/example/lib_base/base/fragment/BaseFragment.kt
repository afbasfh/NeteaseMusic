package com.example.lib_base.base.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.viewbinding.ViewBinding

abstract class BaseFragment:Fragment() {
    private var _binding:ViewBinding?=null
    val binding:ViewBinding?
    get()=_binding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val resId=getLayoutId()
        if (resId!=0){
            return inflater.inflate(resId,container,false)
        }
        _binding=getLayoutBinding(inflater)
        if (_binding!=null){
            return _binding!!.root
        }
        return null
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initData()
    }

    /***
     * 获取布局的资源Id
     */
   open fun getLayoutId():Int=0

    /***
     * 获取绑定类对象
     */
    open fun getLayoutBinding(inflater: LayoutInflater):ViewBinding?=null

   open fun initData(){}
}