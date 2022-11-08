/**
 * Created by Richard -> MHS
 * Date : 2022/11/3  17:13
 * Version : 1
 * 自定义 View 制作 view 的各边约束
 */
@file: JvmName("Constraint")

package com.devmeng.baselib.normal

import androidx.constraintlayout.widget.ConstraintSet

/**
 * 自定义 View 制作 view 约束与父级各边
 * @param viewId 添加约束的 控件 id
 */
fun csParent(viewId: Int): ConstraintSet {
    val cs = ConstraintSet()
    cs.connect(viewId, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START)
    cs.connect(viewId, ConstraintSet.END, ConstraintSet.PARENT_ID, ConstraintSet.END)
    cs.connect(viewId, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP)
    cs.connect(viewId, ConstraintSet.BOTTOM, ConstraintSet.PARENT_ID, ConstraintSet.BOTTOM)
    return cs
}

/**
 * 自定义 View 制作 view 约束与父级各边
 * @param viewId 添加约束的 控件 id
 */
fun csTTSE(
    targetViewId: Int = ConstraintSet.PARENT_ID,
    currentViewId: Int
): ConstraintSet {
    val cs = ConstraintSet()
    cs.connect(currentViewId, ConstraintSet.START, targetViewId, ConstraintSet.START)
    cs.connect(currentViewId, ConstraintSet.END, targetViewId, ConstraintSet.END)
    cs.connect(currentViewId, ConstraintSet.TOP, targetViewId, ConstraintSet.TOP)
    return cs
}

/**
 * 制作 view 约束目标控件的 下方 及 左右
 * @param targetViewId 目标的控件 id
 * @param currentViewId 需添加约束的 控件 id
 */
fun csT2BSE(
    targetViewId: Int, currentViewId: Int
): ConstraintSet {
    val cs = ConstraintSet()
    cs.connect(currentViewId, ConstraintSet.START, targetViewId, ConstraintSet.START)
    cs.connect(currentViewId, ConstraintSet.END, targetViewId, ConstraintSet.END)
    cs.connect(currentViewId, ConstraintSet.TOP, targetViewId, ConstraintSet.BOTTOM)
    return cs
}
