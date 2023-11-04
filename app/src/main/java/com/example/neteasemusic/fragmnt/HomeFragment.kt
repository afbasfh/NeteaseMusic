package com.example.neteasemusic.fragmnt

import android.Manifest
import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.lib_base.bmob.MusicManager
import com.example.neteasemusic.MainActivity
import com.example.neteasemusic.R
import com.example.neteasemusic.adapter.MusicAdapter
import com.example.neteasemusic.databinding.FragmentHomeBinding


class HomeFragment : Fragment() {
    private var mPostNotificationLauncher: ActivityResultLauncher<String>? = null
    private var hasPostNotificationPermission  = true
    lateinit var binding:FragmentHomeBinding
    val adapter=MusicAdapter()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment

        binding=FragmentHomeBinding.inflate(inflater,container,false)
        binding.ivIcon.layoutManager=LinearLayoutManager(requireContext())
        binding.ivIcon.adapter=adapter
        MusicManager.instance.loadMusics(
            onStart = {},
            callback = {
                adapter.setMusics(it)
            }

        )
        initPostNotification()
        requestPostNotification()
        return binding.root
    }
    private fun initPostNotification(){
        mPostNotificationLauncher = registerForActivityResult(ActivityResultContracts.RequestPermission()){
            hasPostNotificationPermission = it
        }
    }

    /**
     * 请求PostNotification权限
     */
    fun requestPostNotification(){
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (!hasPostNotificationPermission) {
                mPostNotificationLauncher?.launch(Manifest.permission.POST_NOTIFICATIONS)
            }
        }
    }

}