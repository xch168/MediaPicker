package com.github.xch168.mediapicker

import android.content.Context
import android.os.Handler
import android.os.HandlerThread
import android.os.Looper
import android.provider.MediaStore

/**
 * Created by XuCanHui on 2020/3/26.
 */
class MediaLoader {

    private var mUiHandler = Handler(Looper.getMainLooper())
    private var mThreadHandler: Handler
    private var mHandlerThread: HandlerThread = HandlerThread("media-loader");

    init {
        mHandlerThread.start()
        mThreadHandler = Handler(mHandlerThread.looper)
    }

    fun loadVideo(context: Context, callback: Callback) {
        mThreadHandler.post {
            val videoList = loadVideo(context)
            mUiHandler.post {
                callback.onLoadComplete(videoList)
            }
        }
    }

    fun loadImage(context: Context, callback: Callback) {
        mThreadHandler.post {
            val imageList = loadImage(context)
            mUiHandler.post {
                callback.onLoadComplete(imageList)
            }
        }
    }

    private fun loadVideo(context: Context): MutableList<Media.Video> {
        val contentResolver = context.contentResolver
        val videoList = mutableListOf<Media.Video>()
        val mediaColumns = arrayOf(
            MediaStore.Video.VideoColumns._ID,
            MediaStore.Video.VideoColumns.DISPLAY_NAME,
            MediaStore.Video.VideoColumns.DATA,
            MediaStore.Video.VideoColumns.DURATION,
            MediaStore.Video.VideoColumns.SIZE)
        val cursor = contentResolver.query(MediaStore.Video.Media.EXTERNAL_CONTENT_URI, mediaColumns,
            null, null, MediaStore.Video.Media.DATE_ADDED + " desc")
        if (cursor == null || cursor.count == 0) {
            return videoList
        }
        while (cursor.moveToNext()) {
            val video = Media.Video()
            video.path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA))
            video.name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DISPLAY_NAME))
            video.duration = cursor.getLong(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DURATION))
            video.size = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Video.Media.SIZE))
            videoList.add(video)
        }
        cursor.close()
        return videoList
    }

    private fun loadImage(context: Context): MutableList<Media.Image> {
        val contentResolver = context.contentResolver
        val imageList = mutableListOf<Media.Image>()
        val mediaColumns = arrayOf(
            MediaStore.Images.Media.DISPLAY_NAME,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.DESCRIPTION,
            MediaStore.Video.VideoColumns.SIZE)
        val cursor = contentResolver.query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, mediaColumns,
            null, null, MediaStore.Images.Media.DATE_ADDED + " desc")
        if (cursor == null || cursor.count == 0) {
            return imageList
        }
        while (cursor.moveToNext()) {
            val image = Media.Image()
            image.path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA))
            image.name = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DISPLAY_NAME))
            image.size = cursor.getInt(cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE))
            imageList.add(image)
        }
        cursor.close()
        return imageList
    }

    interface Callback {
        fun onLoadComplete(mediaList: MutableList<out Media>)
    }
}