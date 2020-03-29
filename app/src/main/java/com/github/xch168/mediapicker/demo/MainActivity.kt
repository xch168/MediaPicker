package com.github.xch168.mediapicker.demo

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.github.xch168.mediapicker.Media
import com.github.xch168.mediapicker.MediaPicker
import com.github.xch168.mediapicker.formatTime

class MainActivity : AppCompatActivity() {

    private lateinit var mThumbView: ImageView
    private lateinit var mDurationView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        mThumbView = findViewById(R.id.iv_thumbnail)
        mDurationView = findViewById(R.id.tv_duration)
    }

    fun pickVideo(view: View) {
        MediaPicker.toPickVideo(this)
    }

    fun pickImage(view: View) {
        MediaPicker.toPickImage(this)
    }

    fun pickMultiVideo(view: View) {
        MediaPicker.toPickVideo(this, MediaPicker.CHOICE_MODE_MULTIPLE, MediaEditorActivity::class.java)
    }
    fun pickMultiImage(view: View) {
        MediaPicker.toPickImage(this, MediaPicker.CHOICE_MODE_MULTIPLE, MediaEditorActivity::class.java)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MediaPicker.PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val media = data?.getParcelableExtra<Media>(MediaPicker.RESULT_MEDIA)
            if (media != null) {
                Glide.with(this).load(media.path).placeholder(R.drawable.mp_ic_thumb_default).into(mThumbView)
                if (media is Media.Video) {
                    mDurationView.text = formatTime(media.duration)
                    mDurationView.visibility = View.VISIBLE
                } else {
                    mDurationView.visibility = View.GONE
                }
            }
        }
    }
}
