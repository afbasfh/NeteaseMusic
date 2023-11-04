package com.example.lib_base.bmob

import kotlin.random.Random

class AdvManager private constructor(){
    val mBmobManager=BmobManager.getInstance()
    var mAdvList= emptyList<Adv>()
    private var preLoadIndex=-1
   companion object{
       val instance:AdvManager by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
           AdvManager()
       }
   }

    /***
     * 加载广告图片url资源
     */
    fun loadAdvs(callBack:(Boolean)->Unit={}){
        mBmobManager.queryAdv(onEnd = {
            if (!it.isNullOrEmpty()){
                mAdvList=it
                callBack(true)
            }else{
                callBack(false)
            }
        })
    }

    /**
     * 随机获取一张Adv对象
     */
    fun getAdv():Adv?{
        if (mAdvList.isEmpty())return null
        if (preLoadIndex==-1){
            preLoadIndex=Random.nextInt(mAdvList.size)
        }
        return mAdvList[preLoadIndex]
    }
}