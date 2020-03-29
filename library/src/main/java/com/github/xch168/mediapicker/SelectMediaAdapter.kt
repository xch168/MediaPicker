package com.github.xch168.mediapicker

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by XuCanHui on 2020/3/28.
 */
class SelectMediaAdapter : RecyclerView.Adapter<SelectMediaAdapter.ViewHolder>() {
    private var mediaList = ArrayList<Media>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(View.inflate(parent.context, R.layout.mp_view_media_select_item, null))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val media = mediaList[position]
        Glide.with(holder.itemView).load(media.path).placeholder(R.drawable.mp_ic_thumb_default).into(holder.thumbView)
        holder.closeView.setOnClickListener {
            mediaList.removeAt(position)
            notifyItemRemoved(position)
            notifyItemRangeChanged(position, mediaList.size - position)
        }
    }

    override fun getItemCount(): Int {
        return mediaList.size
    }

    fun addMedia(media: Media) {
        mediaList.add(media)
        notifyDataSetChanged()
    }

    fun getMediaList(): ArrayList<Media> {
        return mediaList
    }

    fun move(fromPos: Int, toPos: Int) {
        if (fromPos < toPos) {
            for (i in fromPos until toPos) {
                Collections.swap(mediaList, i, i + 1)
            }
        } else {
            for (i in fromPos until toPos) {
                Collections.swap(mediaList, i, i - 1)
            }
        }
        notifyItemMoved(fromPos, toPos)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var thumbView: ImageView = itemView.findViewById(R.id.iv_thumbnail)
        var closeView: ImageView = itemView.findViewById(R.id.iv_close)
    }
}