package com.example.login

import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.databinding.BindingAdapter
import androidx.navigation.findNavController
import com.example.lib_common_ui.AnimUtils
import com.example.lib_common_ui.LGHAlertView
import com.example.login.utils.ViewUtils

class LgClickEvents {
    /***
     * 输入框右边删除按钮点击 清空手机号
     */
    @BindingAdapter("clearPhone")
    fun clearPhone(view:View,viewModel: LgLoginViewModel){
        viewModel.clearPhoneNumber()
    }

    @BindingAdapter("login")
    fun login(view: View,viewModel: LgLoginViewModel) {
        /***
         * 用户是否同意协议
         */
        val phone = viewModel.phoneNumber.value
        val mView=LayoutInflater.from(view.context).inflate(R.layout.layout_login_alert,null)
        if (phone == null || phone.length != 11) {
            AnimUtils.startSwingAnimation(view)
            ViewUtils.showAlert(view.context,"手机号应为11位数",false)
            return
        }
            if (!viewModel.isChecked.value!!) {
                viewModel.agreementShouldSwing.postValue(true)
                ViewUtils.showAlert(view.context,"同意协议后继续",false)
                return
            }


        view.findNavController().navigate(R.id.action_lgPhoneFragment_to_lgVerifyFragment)



    }

    /***
     * 同意协议的按钮点击事件
     */
    @BindingAdapter("changeSelectState")
    fun changeSelectState(view: View,viewModel: LgLoginViewModel){
    viewModel.isChecked.postValue(!viewModel.isChecked.value!!)
    }
}