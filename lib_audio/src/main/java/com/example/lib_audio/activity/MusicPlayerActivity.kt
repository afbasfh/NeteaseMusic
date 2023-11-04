package com.example.lib_audio.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.example.lib_audio.R
import com.example.lib_audio.bottom.AlbumRotateAnimation
import com.example.lib_audio.bottom.ImageLoader
import com.example.lib_audio.databinding.ActivityMusicPlayerBinding
import com.example.lib_audio.events.*
import com.example.lib_audio.player.AudioController
import com.example.lib_base.bmob.Song
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class MusicPlayerActivity : AppCompatActivity() {
    private lateinit var mRotateAnimation:AlbumRotateAnimation
    private lateinit var Binding :ActivityMusicPlayerBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Binding=ActivityMusicPlayerBinding.inflate(layoutInflater)

        //注册EventBus
        EventBus.getDefault().register(this)
        //创建旋转动画对象
        mRotateAnimation = AlbumRotateAnimation(Binding.albumView)
        //关闭当前界面
        Binding.closeView.setOnClickListener { finish() }
        //下一曲
        Binding.nextView.setOnClickListener { AudioController.instance.playNext() }
        //上一曲
        Binding.previousView.setOnClickListener { AudioController.instance.playPrevious() }
        //播放或者暂停
        Binding.playView.setOnClickListener { AudioController.instance.playOrPause() }

        //初始化当前音乐信息
        initLoadUi(AudioController.instance.getNowPlayingMusic())
        if (AudioController.instance.isPlaying()){
            showPlayStatus()
        }
        //切换播放模式
        Binding.modeView.setOnClickListener {
            AudioController.instance.changePlayMode()
        }
        //收藏和取消收藏
        Binding.favoriteView.setOnClickListener {
            AudioController.instance.changeFavoriteStatus()
        }
        setContentView(Binding.root)
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoadEvent(event: AudioLoadEvent){
        initLoadUi(event.music)
        mRotateAnimation.reset()
        Binding.progressView.reset()
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onStartEvent(event: AudioStartEvent){
       showPlayStatus()

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPauseEvent(event: AudioPauseEvent){
        showPauseStatus()

    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onProgressChangeEvent(event: AudioProgressChangeEvent){
        Binding.timeStartView.text=TimerUtils.stringFromTime(event.now)
        Binding.timeEndView.text=TimerUtils.stringFromTime(event.total)
        Binding.progressView.changeProgress(event.now.toFloat()/event.total.toFloat())
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPlayModeChangeEvent(event: AudioPlayModeChangeEvent){
        when(event.mode){
            AudioController.PlayMode.RANDOM ->{Binding.modeView.setImageResource(R.drawable.audio_mode_random)}
            AudioController.PlayMode.LOOP ->{Binding.modeView.setImageResource(R.drawable.audio_mode_list_cycle)}
            AudioController.PlayMode.REPEAT ->{Binding.modeView.setImageResource(R.drawable.audio_mode_recycle)}
        }
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onFavoriteStatusChangeEvent(event: AudioFavoriteStatusChangeEvent){
        if (event.isFavorite){
            Binding.favoriteView.setImageResource(R.drawable.audio_selected_love)
        }else{
            Binding.favoriteView.setImageResource(R.drawable.audio_love)
        }
    }    /**
     * 初始化音乐界面
     */
    private fun initLoadUi(music:Song){
        Binding.titleView.text=music.title
        Binding.authorView.text=music.title
        Binding.timeEndView.text=TimerUtils.stringFromTime(music.duration)
        ImageLoader.loadCircleImage(music.album,Binding.albumView)
    }
    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
    /**
     * 播放状态的操作
     */
    private fun showPlayStatus(){
        mRotateAnimation.start()
        Binding.playView.setImageResource(R.drawable.audio_stop)
        IndicatorAnimaiton.startAnimation(Binding.indicatorView)
    }

    /**
     * 暂停状态的操作
     */
    private fun showPauseStatus(){
        mRotateAnimation.pause()
        IndicatorAnimaiton.stopAnimation(Binding.indicatorView)
        Binding.playView.setImageResource(R.drawable.audio_play)
    }
}