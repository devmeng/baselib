@file: JvmName("ILayoutParams")

package com.devmeng.baselib.normal

import android.view.ViewGroup.LayoutParams

/**
 * Created by Richard -> MHS
 * Date : 2022/11/3  18:14
 * Version : 1
 */

fun wrapper(): LayoutParams = LayoutParams(
    LayoutParams.WRAP_CONTENT,
    LayoutParams.WRAP_CONTENT
)

fun lp(width: Int, height: Int) = LayoutParams(
    width, height
)

