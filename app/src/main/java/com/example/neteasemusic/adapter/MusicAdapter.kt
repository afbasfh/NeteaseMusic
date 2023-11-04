package com.example.neteasemusic.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.lib_audio.player.AudioController
import com.example.lib_audio.service.MusicService
import com.example.lib_base.bmob.Song

import com.example.neteasemusic.databinding.LayoutMusicItemBinding


class MusicAdapter: RecyclerView.Adapter<MusicAdapter.MyViewHolder>() {
    //保存列表歌曲
    private var mMusics = emptyList<Song>()

    //创建ViewHodler
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val inflator = LayoutInflater.from(parent.context)
        return MyViewHolder(LayoutMusicItemBinding.inflate(inflator,parent,false))
    }

    //配置有多个item
    override fun getItemCount(): Int {
        return mMusics.size
    }
    //将当前position对应的歌曲绑定到ViewHodler的控件中
    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(mMusics[position])
    }

    class MyViewHolder(val binding: LayoutMusicItemBinding):RecyclerView.ViewHolder(binding.root){
        fun bind(music:Song){
            binding.titleView.text = music.title
            binding.authorView.text = music.author
            binding.billboardView.text = music.type
            Glide.with(binding.root.context)
                .load(music.album)
                .into(binding.albumView)
            binding.root.setOnClickListener {//给Item添加点击事件
                //播放音乐
               MusicService.startMusicService(music)
            }
        }
    }
    /**
     * 设置音乐
     */
    fun setMusics(musics:List<Song>){
        mMusics = musics
        notifyDataSetChanged()
    }
}












