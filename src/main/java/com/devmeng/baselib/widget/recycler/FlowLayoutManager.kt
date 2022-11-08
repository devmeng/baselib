package com.devmeng.baselib.widget.recycler

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView

/**
 * Created by Richard -> MHS
 * Date : 2022/10/25  18:33
 * Version : 1
 */
class FlowLayoutManager : RecyclerView.LayoutManager() {

    private var offsetAll = 0

    override fun generateDefaultLayoutParams(): RecyclerView.LayoutParams {
        return RecyclerView.LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
    }

    override fun onLayoutChildren(recycler: RecyclerView.Recycler?, state: RecyclerView.State?) {
        super.onLayoutChildren(recycler, state)
        if ((itemCount <= 0).or(state!!.isPreLayout)) {
            return
        }

    }
}