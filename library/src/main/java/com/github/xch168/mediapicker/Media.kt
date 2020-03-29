package com.github.xch168.mediapicker

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by XuCanHui on 2020/3/28.
 */
@Parcelize
open class Media(open var id: Int = 0, open var path: String = "", open var name: String? = "", open var size: Int = 0) : Parcelable {

    @Parcelize
    class Video(override var id: Int = 0, override var path: String = "", override var name: String? = "", override var size: Int = 0, var duration: Long = 0) : Media()

    @Parcelize
    class Image(override var id: Int = 0, override var path: String = "", override var name: String? = "", override var size: Int = 0) : Media()
}