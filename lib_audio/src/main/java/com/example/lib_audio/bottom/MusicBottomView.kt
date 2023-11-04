package com.example.lib_audio.bottom
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.FrameLayout
import com.example.lib_audio.activity.MusicPlayerActivity
import com.example.lib_audio.databinding.MusicBottomViewBinding
import com.example.lib_audio.events.AudioLoadEvent
import com.example.lib_audio.events.AudioPauseEvent
import com.example.lib_audio.events.AudioProgressChangeEvent
import com.example.lib_audio.events.AudioStartEvent
import com.example.lib_audio.player.AudioController
import com.example.lib_audio.player.AudioHelper
import com.example.lib_base.bmob.Song
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * a. 这个视图有多个子视图  需要ViewGroup或者他的子类
 *   - 继承于ViewGroup 重写onMeasure和onLayout方法 自己确定自己的大小和子控件摆放的位置
 *   - 继承于ViewGroup的子类 LinearLayout FrameLayout RelativeLayout ConstraintLayout
 *       子类内部已经实现了自己如何测量和摆放的逻辑
 *       1. 将xml中的布局文件解析出来  -> viewgroup整体A
 *       2. 将整体A添加到当前容器中
 *
 */
class MusicBottomView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {
    private var mBinding: MusicBottomViewBinding
    private var mRotateAnimation:AlbumRotateAnimation
    init {
        //1.解析布局 得到一个整体的视图
        mBinding = MusicBottomViewBinding.inflate(LayoutInflater.from(context))
        //2.将整体的视图添加到当前容器中
        //2.1 确定这个控件的布局参数：宽 和 高
        val lp = LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.WRAP_CONTENT)
        addView(mBinding.root,lp)
//        注册Eventbus
        EventBus.getDefault().register(this)
//        初始化动画对象
        mRotateAnimation = AlbumRotateAnimation(mBinding.cover)
//        点击进入独立播放页面
        mBinding.root.setOnClickListener {

            val intent=Intent(context,MusicPlayerActivity::class.java)
            context.startActivity(intent)
        }
//            播放暂停按钮的点击事件
        mBinding.progressPlayView.setOnClickListener {
            AudioController.instance.playOrPause()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
//        取消订阅
        EventBus.getDefault().unregister(this)
    }
    /**
     * 订阅自己需要的事件
     * 1. 参数必须是发送时对应的类型
     * 2. 使用@Subcribe注解修饰方法
     */
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onLoadEvent(event:AudioLoadEvent){
        initLoadUi(event.music)
        mRotateAnimation.reset()
        mBinding.progressPlayView.showPause()
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onStartEvent(event: AudioStartEvent){
        mRotateAnimation.start()
        mBinding.progressPlayView.showPlay()
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onPauseEvent(event: AudioPauseEvent){
        mRotateAnimation.pause()
        mBinding.progressPlayView.showPause()
    }
    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onProgressChangeEvent(event: AudioProgressChangeEvent){
        val progress =event.now.toFloat()/event.total.toFloat()
        mBinding.progressPlayView.changeProgress(progress)
    }

    @SuppressLint("SetTextI18n")
    private fun initLoadUi(music:Song){
        mBinding.textView.text="${music.title}-${music.author}"
        ImageLoader.loadCircleImage(music.album,mBinding.cover)
    }
}