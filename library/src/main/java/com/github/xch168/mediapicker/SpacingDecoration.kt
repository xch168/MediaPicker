package com.github.xch168.mediapicker

import android.content.Context
import android.graphics.Rect
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by XuCanHui on 2020/3/27.
 */
class SpacingDecoration(context: Context, hSpacing: Float, vSpacing: Float) : RecyclerView.ItemDecoration() {

    private val mHSpacing = dp2px(context, hSpacing)
    private val mVSpacing = dp2px(context, vSpacing)

    override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
        super.getItemOffsets(outRect, view, parent, state)

        val position = parent.getChildAdapterPosition(view)
        if (parent.layoutManager is GridLayoutManager) {
            val layoutManager = parent.layoutManager as GridLayoutManager
            val spanCount = layoutManager.spanCount
            val column = position % spanCount
            getGridItemOffset(outRect, position, column, spanCount)
        }

    }

    private fun getGridItemOffset(outRect: Rect, position: Int, column: Int, spanCount: Int) {
        outRect.left = mHSpacing * (spanCount - column) / spanCount
        outRect.right = mHSpacing * (column + 1) / spanCount
        if (position < spanCount) {
            outRect.top = mVSpacing
        }
        outRect.bottom = mVSpacing
    }
}