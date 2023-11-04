package com.example.neteasemusic.bottombar

data class TabBarItem(
    val normalRes:Int,
    val selectRes:Int,
    val title:String,
    var index:Int=0,
    var selected:Boolean=false
) {
}