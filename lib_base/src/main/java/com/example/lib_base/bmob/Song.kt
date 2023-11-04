package com.example.lib_base.bmob

import cn.bmob.v3.BmobObject
import java.time.Duration

class Song(
    val type: String,//榜单名称
    val title: String,//歌曲名称
    val author: String,//歌手
    val album: String,//封面图片
    val url: String,//歌曲地址
    val isVip: Boolean,
    val favoriteCount: Int,//点赞数
    var duration: Int
) : BmobObject() {
    override fun toString(): String {
        return "$title-$author"
    }
}