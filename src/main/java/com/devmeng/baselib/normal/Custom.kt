@file: JvmName("Custom")

package com.devmeng.baselib.normal

import android.content.Context
import android.util.TypedValue
import java.lang.ref.WeakReference

/**
 * Created by Richard -> MHS
 * Date : 2022/11/3  16:41
 * Version : 1
 */
fun toDp(context: Context, value: Float): Int {
    val c = WeakReference(context).get()
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        value,
        c?.resources?.displayMetrics
    ).toInt()
}

fun toPx(context: Context, value: Float): Int {
    val c = WeakReference(context).get()
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_PX,
        value,
        c?.resources?.displayMetrics
    ).toInt()
}

fun getColor(context: Context, color: Int): Int = context.getColor(color)