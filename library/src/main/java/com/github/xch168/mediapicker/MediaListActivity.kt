package com.github.xch168.mediapicker

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class MediaListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_list)
    }

    companion object {
        fun open(context: Context) {
            val intent: Intent = Intent(context, MediaListActivity.javaClass)
            context.startActivity(intent)
        }
    }
}
