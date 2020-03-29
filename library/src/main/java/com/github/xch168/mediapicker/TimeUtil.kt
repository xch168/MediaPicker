package com.github.xch168.mediapicker

/**
 * Created by XuCanHui on 2020/3/28.
 */
fun formatTime(timeMs: Long): String {
    var str = ""
    val timeS = timeMs / 1000
    val s = timeS % 60
    val m = timeS / 60 % 60
    val h = timeS / 3600
    if (h > 0) {
        str = "$str$h:"
    }
    if (m <= 9) {
        str = "${str}0$m:"
    } else {
        str = "$str$m:"
    }
    if (s <= 9) {
        str = "${str}0$s"
    } else {
        str = "$str$s"
    }
    return str
}