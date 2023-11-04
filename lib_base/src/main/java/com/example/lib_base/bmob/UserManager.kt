package com.example.lib_base.bmob

class UserManager private constructor() {
    private var mUser: User? = null
    private var mBmob = BmobManager.getInstance()

    companion object {
        private var instance: UserManager? = null
        fun getInstance(): UserManager {
            if (instance == null) {
                synchronized(this) {
                    if (instance == null) {
                        instance = UserManager()
                    }
                }
            }
            return instance!!
        }
    }

    //    是否登陆
    fun isLogin(): Boolean {
        return BmobManager.getInstance().isLogin()
    }

    fun requestSms(phone: String, onStart: () -> Unit = {}, onEnd: (Boolean) -> Unit) {
        onStart()
        mBmob.requestSms(phone) {
            onEnd(false)
        }
    }

    //    短信验证码登录
    fun loginBySmsCode(
        phone: String,
        code: String,
        onStart: () -> Unit = {},
        onEnd: (Boolean) -> Unit
    ) {
        onStart()
        mBmob.signOrLoginByMobilePhone(phone, code) {
            if (it) {
                mUser = mBmob.currentUser()
            }
            onEnd(it)
        }
    }

    fun loginByPhone(phone: String,password:String,onStart: () -> Unit = {}, onEnd: (Boolean) -> Unit){
        onStart()
        mBmob.loginByPhone(phone,password){
            if (it){
                mUser=mBmob.currentUser()
            }
            onEnd(it)
        }
    }
    fun logout(){
        mUser=null
        mBmob.logout()
    }
}