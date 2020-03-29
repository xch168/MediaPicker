package com.github.xch168.mediapicker

import android.Manifest
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat

/**
 * Created by XuCanHui on 2020/3/25.
 */
class MediaPicker {
    companion object {
        const val RESULT_MEDIA = "media"
        const val RESULT_MEDIA_LIST = "mediaList"

        const val PICK_REQUEST_CODE = 0x100;
        const val PERMISSION_REQUEST_CODE = 0x101

        const val MEDIA_TYPE_VIDEO = 0
        const val MEDIA_TYPE_IMAGE = 1

        const val CHOICE_MODE_SINGLE = 0x200
        const val CHOICE_MODE_MULTIPLE = 0x201

        private val permissions = arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE)

        private fun toPick(context: Context, mediaType: Int, choiceMode: Int, nextAction: Class<out Any>? = null) {
            if (checkPermission(context)) {
                MediaListActivity.open(context, mediaType, choiceMode, nextAction)
            } else {
                requestPermission(context)
            }
        }

        @JvmStatic
        @JvmOverloads
        fun toPickVideo(context: Context, choiceMode: Int = CHOICE_MODE_SINGLE, nextAction: Class<out Any>? = null) {
            toPick(context, MEDIA_TYPE_VIDEO, choiceMode, nextAction)
        }

        @JvmStatic
        @JvmOverloads
        fun toPickImage(context: Context, choiceMode: Int = CHOICE_MODE_SINGLE, nextAction: Class<out Any>? = null) {
            toPick(context, MEDIA_TYPE_IMAGE, choiceMode, nextAction)
        }

        private fun checkPermission(context: Context): Boolean {
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
                return true
            }
            return ContextCompat.checkSelfPermission(context, permissions[0]) == PackageManager.PERMISSION_GRANTED
        }

        private fun requestPermission(context: Context) {
            ActivityCompat.requestPermissions(context as Activity, permissions, PERMISSION_REQUEST_CODE)
        }
    }
}