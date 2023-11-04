package com.example.login.verify

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.login.LgLoginViewModel
import com.example.login.R
import com.example.login.databinding.FragmentLgVerifyBinding
import com.example.login.utils.ViewUtils
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LgVerifyFragment : Fragment() {
    private lateinit var binding: FragmentLgVerifyBinding
    private val viewModel: LgLoginViewModel by activityViewModels()
    private var WAIT_TIME = 60
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentLgVerifyBinding.inflate(inflater, container, false)
        return binding.root
    }

    //发送验证码
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel.time=WAIT_TIME
        binding.textView7.text = "$WAIT_TIME"
//        requestSms()
//        if (viewModel.time<=0){
//        requestSms()
//        }
        binding.textView6.text=viewModel.phoneNumber.value
        binding.textView7.setOnClickListener {
            if (binding.textView7.text != "重新获取") return@setOnClickListener
            viewModel.time = 60
//            requestSms()
        }
//
        binding.verifyEditText.addTextChangedListener{
            if (it?.length==6){
                viewModel.verifyAndLogin(it.toString()){
                    if (it){
                        requireActivity().finish()
                    }else{
                        ViewUtils.showAlert(requireContext(),"验证码错误",false)
                    }
                }
            }
        }
//        点击返回上一个页面
        binding.imageView3.setOnClickListener {
            findNavController().navigate(R.id.action_lgVerifyFragment_to_lgPhoneFragment)
        }
//        密码登录
        binding.passwodLoginBtn.setOnClickListener {
            findNavController().navigate(R.id.action_lgVerifyFragment_to_lgPasswordFragment)
        }
    }

    private fun requestSms() {
        Log.v("lgh","ddd")
        viewModel.requestSms(
            onStart = {
            ViewUtils.showAlert(requireContext(),"短信已发送成功",true)
            lifecycleScope.launch {
                while (true) {
                    delay(1000)
                    viewModel.time--
                    if (viewModel.time == -1) {
                        binding.textView7.text = "重新获取"
                        break
                    }
                    binding.textView7.text = "${viewModel.time}"
                }
            }
        },
            onEnd = {
                if(it){
                    ViewUtils.showAlert(requireContext(),"短信已发送成功",true)
                }else{
                    ViewUtils.showAlert(requireContext(),"请求验证码失败",true)
                }
            })
    }
}