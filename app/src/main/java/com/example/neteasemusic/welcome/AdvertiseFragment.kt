package com.example.neteasemusic.welcome

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.lib_base.bmob.AdvManager
import com.example.lib_base.utils.CountTimer
import com.example.lib_base.utils.navigateTo
import com.example.login.LoginActivity
import com.example.neteasemusic.MainActivity

import com.example.neteasemusic.databinding.FragmentAdvertiseBinding
import navigate


class AdvertiseFragment : Fragment() {
    lateinit var binding:FragmentAdvertiseBinding
    private lateinit var mCountTimer: CountTimer
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /**
         * 加载图片
         */
        val adv = AdvManager.instance.getAdv()
        adv?.let {
            //加载图片
            Glide.with(this).load(it.url).into(binding.ivAdvertiesment)
        }
        binding=FragmentAdvertiseBinding.inflate(inflater,container,false)
        binding.textView10.setOnClickListener {
            jump()
        }
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onResume() {
        super.onResume()
        binding.textView10.text="跳转5"
//        倒计时
        mCountTimer=CountTimer().timeInterval(1000)
            .order(CountTimer.Order.DOWN)
            .startValue(5)
            .setCallBack{countTimer,value->
                binding.textView10.text="跳转$value"
                if (value==0){
                    countTimer.stop()
                    requireActivity().navigateTo(LoginActivity::class.java)
                }
            }
        mCountTimer.start()

    }

    fun  jump(){
        mCountTimer.stop()
        requireActivity().navigateTo(MainActivity::class.java)
    }

}