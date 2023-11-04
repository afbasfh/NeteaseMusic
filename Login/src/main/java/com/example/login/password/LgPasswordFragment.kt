package com.example.login.password

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.example.login.LgLoginViewModel
import com.example.login.R
import com.example.login.databinding.FragmentLgPasswordBinding
import com.example.login.utils.ViewUtils


class LgPasswordFragment : Fragment() {
    private lateinit var binding:FragmentLgPasswordBinding
    private val viewModel:LgLoginViewModel by activityViewModels()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
       binding=FragmentLgPasswordBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
//        返回上一层
        binding.backBotton.setOnClickListener {
            findNavController().navigateUp()
            ViewUtils.dismissionLoading()
        }

        binding.passwordEditText.addTextChangedListener{
            if (it==null){
                binding.textView2.isEnabled=false
            }else{
                binding.textView2.isEnabled=it.isNotEmpty()
            }
        }
//登录按钮
        binding.textView2.setOnClickListener {
            val password=binding.passwordEditText.text.toString()
            viewModel.loginByPassword(password,
            onStart = {
                ViewUtils.showIsLoading(requireContext())
            },
            onEnd = {
               if (!it){
                   requireActivity().finish()
               }else{
                   ViewUtils.dismissionLoading()
                   ViewUtils.showAlert(requireContext(),"密码错误",false)
                   binding.passwordEditText.setText("")
               }
            })

        }
    }

}