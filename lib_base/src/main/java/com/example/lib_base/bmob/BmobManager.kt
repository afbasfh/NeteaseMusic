package com.example.lib_base.bmob

import cn.bmob.v3.BmobQuery
import cn.bmob.v3.BmobSMS
import cn.bmob.v3.BmobUser
import cn.bmob.v3.exception.BmobException
import cn.bmob.v3.listener.FindListener
import cn.bmob.v3.listener.LogInListener
import cn.bmob.v3.listener.QueryListener

class BmobManager private constructor() {
    companion object{
        private var instance: BmobManager?=null
        fun getInstance(): BmobManager {
            if (instance ==null){
                synchronized(this){
                    if (instance ==null){
                        instance = BmobManager()
                    }
                }
            }
            return instance!!
        }
    }


    fun isLogin()=BmobUser.isLogin()
//    请求验证码
    fun requestSms(phone:String,onEnd:(Boolean)->Unit={}){
        BmobSMS.requestSMSCode(phone,"",object :QueryListener<Int>(){
            override fun done(p0: Int?, p1: BmobException?) {
                onEnd(p1==null)
            }
        })
    }


    fun currentUser()=BmobUser.getCurrentUser(User::class.java)

    fun signOrLoginByMobilePhone(phone: String,code:String,callback:(Boolean)->Unit={}){
        BmobUser.signOrLoginByMobilePhone(phone,code,object :LogInListener<Int>(){
            override fun done(p0: Int?, p1: BmobException?) {
                callback(p1==null)
            }
        })
    }

    fun loginByPhone(phone: String,password:String,callback: (Boolean) -> Unit){
        BmobUser.loginByAccount(phone,password,object :LogInListener<Int>(){
            override fun done(p0: Int?, p1: BmobException?) {
                callback(p1==null)
            }
        })
    }

    fun logout()=BmobUser.logOut()

    /**
     * 查询Adv表里面的所以信息
     */
    fun queryAdv(onStart:()->Unit={},onEnd: (List<Adv>?) -> Unit={}){
        onStart()
        val aduQuery=BmobQuery<Adv>()
        aduQuery.findObjects(object :FindListener<Adv>(){
            override fun done(p0: MutableList<Adv>?, p1: BmobException?) {
                    onEnd(p0)
            }
        })
    }
    fun queryMusics(onStart:()->Unit={},onEnd: (List<Song>?) -> Unit={}){
        onStart()
        val aduQuery=BmobQuery<Song>()
        aduQuery.findObjects(object :FindListener<Song>(){
            override fun done(p0: MutableList<Song>?, p1: BmobException?) {
                onEnd(p0)
            }
        })
    }

}