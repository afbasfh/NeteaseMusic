package com.example.neteasemusic

import android.Manifest
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.get
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.PagerAdapter
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import cn.bmob.v3.Bmob
import com.example.lib_base.bmob.BmobManager
import com.example.lib_base.bmob.MusicManager


import com.example.lib_base.bmob.UserManager
import com.example.login.LoginActivity
import com.example.neteasemusic.adapter.ViewPagerAdapter
import com.example.neteasemusic.bottombar.BottomTabBar
import com.example.neteasemusic.bottombar.TabBarItem
import com.example.neteasemusic.databinding.ActivityMainBinding

import com.example.neteasemusic.fragmnt.CircleFragment
import com.example.neteasemusic.fragmnt.HomeFragment
import com.example.neteasemusic.fragmnt.MeFragment

class MainActivity : AppCompatActivity() {
    private var mPostNotificationLauncher: ActivityResultLauncher<String>? = null
    private var hasPostNotificationPermission  = true
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        checkIsLogin()

        setUpBottomBar()

        initPostNotification()
        requestPostNotification()
    }

    fun checkIsLogin() {
        if (!UserManager.getInstance().isLogin()) return
        val intent = Intent(this, LoginActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
    }

    fun setUpBottomBar(){
        val vp2=findViewById<ViewPager2>(R.id.viewpager)
        vp2.adapter=ViewPagerAdapter(listOf(HomeFragment(),CircleFragment(),MeFragment()),supportFragmentManager,lifecycle)
        vp2.isUserInputEnabled=false
        val barView=findViewById<BottomTabBar>(R.id.bottomItemBar)
        barView.setupWithBarItems(
            listOf(
                TabBarItem(
                R.drawable.tab_home,
                R.drawable.tab_selected_home,
                "发现",
            ),  TabBarItem(
                    R.drawable.tab_circle,
                    R.drawable.tab_selected_circle,
                    "圈子"
                ),  TabBarItem(
                    R.drawable.tab_me,
                    R.drawable.tab_selected_me,
                    "我"
                )
            )
        ){
            vp2.setCurrentItem(it,false)
        }
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