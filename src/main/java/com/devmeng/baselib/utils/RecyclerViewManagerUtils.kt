package com.devmeng.baselib.utils

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager

/**
 * Created by Richard
 * Version : 1
 * Description :
 */
class RecyclerViewManagerUtils {

    companion object {

        fun configLinearLayoutManager(
            recyclerView: RecyclerView,
            orientation: Int = RecyclerView.VERTICAL,
            reverseLayout: Boolean = false
        ) {
            recyclerView.layoutManager =
                LinearLayoutManager(
                    recyclerView.context.applicationContext,
                    orientation,
                    reverseLayout
                )
        }

        fun configGridLayoutManager(
            recyclerView: RecyclerView, spanCount: Int = 2,
            orientation: Int = RecyclerView.VERTICAL,
            reverseLayout: Boolean = false
        ) {
            recyclerView.layoutManager =
                GridLayoutManager(
                    recyclerView.context.applicationContext,
                    spanCount,
                    orientation,
                    reverseLayout
                )
        }

        fun configStaggeredGridLayoutManager(
            recyclerView: RecyclerView, spanCount: Int = 2,
            orientation: Int = RecyclerView.VERTICAL,
        ) {
            recyclerView.layoutManager =
                StaggeredGridLayoutManager(spanCount, orientation)
        }
    }

}