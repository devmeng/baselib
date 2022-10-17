package com.devmeng.baselib.skin.utils

import android.app.Activity
import android.content.Context
import android.os.Build

/**
 * Created by Richard
 * Version : 1
 * Description :
 * 换肤工具类
 */
class SkinThemeUtils {

    companion object {
        private val APPCOMPAT_COLOR_PRIMARY_VARIANT =
            intArrayOf(androidx.appcompat.R.attr.colorPrimaryDark)

        private val SYSTEM_BAR_ATTRS =
            intArrayOf(android.R.attr.statusBarColor, android.R.attr.navigationBarColor)

        fun updateStatusBarState(activity: Activity) {
            //5.0 以上才能修改
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                return;
            }
            //获取statusBarColor与navigationBarColor  颜色值
            val statusBarId = getResId(activity, SYSTEM_BAR_ATTRS)
            if (statusBarId[0] != 0) {
                //如果statusBarColor配置颜色值，就换肤
                activity.window.statusBarColor = SkinResources.instance.getColor(statusBarId[0])
                return
            }
            //获取皮肤包中的 colorPrimaryVariant，兼容版本
            val resId = getResId(activity, APPCOMPAT_COLOR_PRIMARY_VARIANT)[0]
            if (resId == 0) {
                return
            }
            activity.window.statusBarColor = SkinResources.instance.getColor(resId)
        }

        fun updateNavigationBarState(activity: Activity) {
            //5.0 以上才能修改
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                return;
            }
            //获取statusBarColor与navigationBarColor  颜色值
            val statusBarId = getResId(activity, SYSTEM_BAR_ATTRS)
            if (statusBarId[1] != 0) {
                //如果statusBarColor配置颜色值，就换肤
                activity.window.navigationBarColor =
                    SkinResources.instance.getColor(statusBarId[0]);
                return
            }
            val resId = getResId(activity, APPCOMPAT_COLOR_PRIMARY_VARIANT)[0]
            if (resId == 0) {
                return
            }
            activity.window.navigationBarColor = SkinResources.instance.getColor(resId);
        }

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