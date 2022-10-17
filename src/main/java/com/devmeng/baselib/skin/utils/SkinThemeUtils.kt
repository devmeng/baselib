package com.devmeng.baselib.skin.utils

import android.content.Context

/**
 * Created by Richard
 * Version : 1
 * Description :
 * 换肤工具类
 */
class SkinThemeUtils {

    companion object {

        /**
         * 获取属性对应的资源 id
         * @param context
         * @param attrs 属性集合
         * @return resArr 资源 id 集合
         */
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