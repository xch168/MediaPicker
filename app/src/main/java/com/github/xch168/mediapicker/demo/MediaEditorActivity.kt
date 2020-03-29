package com.github.xch168.mediapicker.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.github.xch168.mediapicker.Media
import com.github.xch168.mediapicker.MediaAdapter
import com.github.xch168.mediapicker.MediaPicker
import com.github.xch168.mediapicker.SpacingDecoration

class MediaEditorActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_media_editor)

        val mediaList = intent.getParcelableArrayListExtra<Media>(MediaPicker.RESULT_MEDIA_LIST)

        val adapter = MediaAdapter()
        val recyclerView: RecyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.addItemDecoration(SpacingDecoration(this, 10f, 10f))
        recyclerView.adapter = adapter
        adapter.setMediaList(mediaList)
    }

}
