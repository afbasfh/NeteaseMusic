package com.example.login.utils

import android.content.Context
import android.view.LayoutInflater
import android.widget.ProgressBar
import android.widget.TextView
import com.example.lib_common_ui.LGHAlertView
import com.example.login.R
import java.lang.ref.WeakReference

var lghAlertView:WeakReference<LGHAlertView>?=null
object ViewUtils {
    /***
     * 弹出顶部的提示试图
     */
    fun showAlert(context: Context,text:String?,isNormal:Boolean){
        val view=if (isNormal)
            LayoutInflater.from(context).inflate(R.layout.layout_login_normal_alert,null)
            else
            LayoutInflater.from(context).inflate(R.layout.layout_login_alert,null)
      if (text!=null){
          view.findViewById<TextView>(R.id.alertTextView).text=text
      }
        LGHAlertView(context, view).show(LGHAlertView.Positon.TOP)
    }
    fun showIsLoading(context: Context){
     val view=LayoutInflater.from(context).inflate(R.layout.loading_layout,null)
        var progressBer=view.findViewById<ProgressBar>(R.id.progressBar)
        val alertView=LGHAlertView(context,view)
        alertView.show(LGHAlertView.Positon.CENTER,false)
        lghAlertView=WeakReference(alertView)
    }

    fun dismissionLoading(){
        lghAlertView?.let {
            it.get()?.dismiss()
        }
    }
}