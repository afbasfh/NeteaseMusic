package com.example.neteasemusic.welcome

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.neteasemusic.R
import com.example.neteasemusic.databinding.FragmentWelcomeBinding
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import navigate


class WelcomeFragment : Fragment() {
    lateinit var binding:FragmentWelcomeBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_welcome, container, false)

    }
    override fun onResume() {
        super.onResume()
        lifecycleScope.launch {
            delay(1000)
            navigate(R.id.welcomeFragmentContainerView,AdvertiseFragment())
        }
    }
}