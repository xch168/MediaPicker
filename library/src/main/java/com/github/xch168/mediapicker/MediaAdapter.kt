package com.github.xch168.mediapicker

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

/**
 * Created by XuCanHui on 2020/3/26.
 */
class MediaAdapter : RecyclerView.Adapter<MediaAdapter.ViewHolder>() {

    private var mediaList = mutableListOf<Media>()

    private var mOnItemClickListener: OnItemClickListener? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(View.inflate(parent.context, R.layout.mp_view_media_item, null))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val media = mediaList[position]
        if (media is Media.Video) {
            holder.durationView.visibility = View.VISIBLE
            holder.durationView.text = formatTime(media.duration)
        }
        Glide.with(holder.itemView).load(media.path).placeholder(R.drawable.mp_ic_thumb_default).into(holder.thumbView)
        holder.itemView.setOnClickListener {
            mOnItemClickListener?.onItemClick(position, media)
        }
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    fun setMediaList(mediaList: MutableList<out Media>) {
        this.mediaList.clear()
        this.mediaList.addAll(mediaList)
        notifyDataSetChanged()
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var thumbView: ImageView = itemView.findViewById(R.id.iv_thumbnail)
        var durationView: TextView = itemView.findViewById(R.id.tv_duration)
    }

    fun setOnItemClickListener(listener: OnItemClickListener) {
        mOnItemClickListener = listener;
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int, media: Media)
    }
}