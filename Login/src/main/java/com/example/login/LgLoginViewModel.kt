package com.example.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lib_base.bmob.UserManager

class LgLoginViewModel:ViewModel() {
    val phoneNumber=MutableLiveData<String>("15839994060")
    var isChecked=MutableLiveData(false)
    var agreementShouldSwing=MutableLiveData(false)
    var time=60
    private val mUserManager= UserManager.getInstance()

    fun clearPhoneNumber(){
        phoneNumber.postValue("")
    }

    /***
     * 请求发送验证码
     */
    fun requestSms(onStart:()->Unit={},onEnd:(Boolean)->Unit={}){
        mUserManager.requestSms(phoneNumber.value!!, onStart = {onStart()}, onEnd = {onEnd(it)})
    }

    /***
     * 验证码一件注册和登录
     */
    fun verifyAndLogin(code:String,onEnd:(Boolean)->Unit={}){
        mUserManager.loginBySmsCode(phoneNumber.value!!, code,onEnd={})
    }

    /***
     * 密码登录
     */
    fun loginByPassword(password:String,onStart:()->Unit={},onEnd:(Boolean)->Unit={}){
        mUserManager.loginByPhone(phoneNumber.value!!,password,onStart,onEnd)
    }
}