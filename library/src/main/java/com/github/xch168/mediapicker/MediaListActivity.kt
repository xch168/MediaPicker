package com.github.xch168.mediapicker

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.Group
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MediaListActivity : AppCompatActivity(), MediaAdapter.OnItemClickListener{

    private lateinit var recyclerView: RecyclerView
    private lateinit var selectRecyclerView: RecyclerView
    private lateinit var nextButton: Button

    private lateinit var adapter: MediaAdapter
    private lateinit var selectAdapter: SelectMediaAdapter

    private lateinit var mMediaLoader: MediaLoader

    private var mMediaType: Int = MediaPicker.MEDIA_TYPE_VIDEO
    private var mChoiceMode: Int = MediaPicker.CHOICE_MODE_SINGLE

    private lateinit var mItemTouchHelper: ItemTouchHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.mp_activity_media_list)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        mMediaType = intent.getIntExtra("mediaType", MediaPicker.MEDIA_TYPE_VIDEO)
        mChoiceMode = intent.getIntExtra("choiceMode", MediaPicker.CHOICE_MODE_SINGLE)

        nextButton = findViewById(R.id.btn_next)

        title = if (mMediaType == MediaPicker.MEDIA_TYPE_VIDEO) "选择视频" else "选择图片"

        val group: Group = findViewById(R.id.group);
        group.visibility = if (mChoiceMode == MediaPicker.CHOICE_MODE_MULTIPLE) {
            initSelectView()
            View.VISIBLE
        } else {
            View.GONE
        }

        adapter = MediaAdapter()
        adapter.setOnItemClickListener(this)
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.addItemDecoration(SpacingDecoration(this, 10f, 10f))
        recyclerView.adapter = adapter

        mMediaLoader = MediaLoader()

        if (mMediaType == MediaPicker.MEDIA_TYPE_VIDEO) {
            mMediaLoader.loadVideo(this, object : MediaLoader.Callback {
                override fun onLoadComplete(mediaList: MutableList<out Media>) {
                    adapter.setMediaList(mediaList)
                }
            })
        } else {
            mMediaLoader.loadImage(this, object : MediaLoader.Callback {
                override fun onLoadComplete(mediaList: MutableList<out Media>) {
                    adapter.setMediaList(mediaList)
                }
            })
        }
    }

    private fun initSelectView() {
        selectAdapter = SelectMediaAdapter()
        selectRecyclerView = findViewById(R.id.select_media_list)
        selectRecyclerView.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        selectRecyclerView.adapter = selectAdapter

        mItemTouchHelper = ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT, 0) {
            override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
                selectAdapter.move(viewHolder.adapterPosition, target.adapterPosition)
                return true
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

        })
        mItemTouchHelper.attachToRecyclerView(selectRecyclerView)
    }

    override fun onItemClick(position: Int, media: Media) {
        if (mChoiceMode == MediaPicker.CHOICE_MODE_MULTIPLE) {
            selectAdapter.addMedia(media)
            nextButton.isEnabled = true
        } else {
            if (sNextAction == null) {
                val data = Intent()
                data.putExtra(MediaPicker.RESULT_MEDIA, media)
                setResult(Activity.RESULT_OK, data)
                finish()
            } else {
                val nextIntent = Intent(this, sNextAction)
                nextIntent.putExtra(MediaPicker.RESULT_MEDIA, media)
                startActivity(nextIntent)
            }
        }
    }

    fun handleNextBtnClick(view: View) {
        val mediaList = selectAdapter.getMediaList()
        if (sNextAction == null) {
            val data = Intent()
            data.putParcelableArrayListExtra(MediaPicker.RESULT_MEDIA_LIST, mediaList)
            setResult(Activity.RESULT_OK, data)
            finish()
        } else {
            val nextIntent = Intent(this, sNextAction)
            nextIntent.putParcelableArrayListExtra(MediaPicker.RESULT_MEDIA_LIST, mediaList)
            startActivity(nextIntent)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    companion object {
        private var sNextAction: Class<out Any>? = null

        fun open(context: Context, mediaType: Int = MediaPicker.CHOICE_MODE_SINGLE,
                 choiceMode: Int = MediaPicker.CHOICE_MODE_SINGLE, nextAction: Class<out Any>? = null) {
            sNextAction = nextAction
            val intent = Intent(context, MediaListActivity::class.java)
            intent.putExtra("mediaType", mediaType)
            intent.putExtra("choiceMode", choiceMode)
            if (nextAction == null) {
                (context as Activity).startActivityForResult(intent, MediaPicker.PICK_REQUEST_CODE)
            } else {
                context.startActivity(intent)
            }
        }
    }

}
