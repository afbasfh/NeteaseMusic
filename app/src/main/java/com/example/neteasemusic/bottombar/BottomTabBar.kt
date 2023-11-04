package com.example.neteasemusic.bottombar

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import com.example.neteasemusic.R

@SuppressLint("UseCompatLoadingForDrawables")
class BottomTabBar(context: Context, attrs:AttributeSet?): FrameLayout(context,attrs){
    private val mTabBarItemViews = arrayListOf<TabBarItemView>()
    private var mCurrentIndex = 0
    private var mLineView = LayoutInflater.from(context).inflate(R.layout.layout_bottombar_top_line,null)
    private val mBarView:LinearLayout
    private lateinit var mBarItems: List<TabBarItem>
    private var mCallBack:((Int)->Unit)? = null
    init {
        //background = resources.getDrawable(R.drawable.bottom_bar_gredient_bg_color,null)
        val linelp = LayoutParams(LayoutParams.MATCH_PARENT,dp2px(1))
        addView(mLineView,linelp)

        mBarView = LinearLayout(context).apply {
            orientation = LinearLayout.HORIZONTAL
            weightSum = 3f
            gravity = Gravity.CENTER_VERTICAL
        }
        val barViewlp = LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT)
        barViewlp.topMargin = dp2px(1)
        addView(mBarView,barViewlp)
    }

    fun setupWithBarItems(items:List<TabBarItem>, callback: (Int)->Unit){
        mBarItems = items
        mCallBack = callback
        items.forEachIndexed { index, tabBarItem ->
            tabBarItem.index = index
            TabBarItemView(context,tabBarItem) { itemIndex ->
                mTabBarItemViews[mCurrentIndex].deSelect()
                mCurrentIndex = itemIndex
                selectItem(mCurrentIndex)
            }.also { tabBarItemView ->
                mTabBarItemViews.add(tabBarItemView)
                val lp = LinearLayout.LayoutParams(0,ViewGroup.LayoutParams.WRAP_CONTENT)
                lp.weight = 1f
                mBarView.addView(tabBarItemView.mView,lp)
            }
        }

        selectItem(mCurrentIndex)
    }

    fun selectItem(index:Int){
        mTabBarItemViews[index].select()
        mCallBack?.let { it(index) }
    }
    fun dp2px(dp:Int) = (context.resources.displayMetrics.density*dp).toInt()
}