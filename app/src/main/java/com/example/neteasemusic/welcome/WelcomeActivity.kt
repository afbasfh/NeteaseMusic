package com.example.neteasemusic.welcome

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.lib_base.bmob.AdvManager
import com.example.lib_base.bmob.BmobManager
import com.example.neteasemusic.R
import com.example.neteasemusic.databinding.FragmentWelcomeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class WelcomeActivity : AppCompatActivity() {
    lateinit var binding: FragmentWelcomeBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)
        binding= FragmentWelcomeBinding.inflate(layoutInflater,null,false)
    }

}