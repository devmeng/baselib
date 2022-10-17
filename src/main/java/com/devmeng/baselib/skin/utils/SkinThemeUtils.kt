package com.devmeng.baselib.skin.utils

import android.content.Context

/**
 * Created by Richard
 * Version : 1
 * Description :
 */
class SkinThemeUtils {

    companion object {

        fun getResId(context: Context, attrs: IntArray): IntArray {
            val resArr = IntArray(attrs.size)
            val typedArray = context.obtainStyledAttributes(attrs)
            for (index in 0 until typedArray.length()) {
                resArr[index] = typedArray.getResourceId(index, 0)
            }
            typedArray.recycle()
            return resArr
        }

    }

}