package com.github.xch168.mediapicker

import android.content.Context

/**
 * Created by XuCanHui on 2020/3/27.
 */
fun dp2px(context: Context, dp: Float): Int {
    val density = context.resources.displayMetrics.density
    return (dp * density + 0.5).toInt()
}