package com.devmeng.baselib.skin.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.drawable.Drawable
import com.devmeng.baselib.utils.ConstantUtils
import com.devmeng.baselib.utils.Logger

/**
 * Created by Richard
 * Version : 1
 * Description :
 */
class SkinResources {

    lateinit var context: Context
    private var skinResources: Resources? = null
    private var skinPkgName: String = ConstantUtils.EMPTY
    private var isDefaultSkin = true

    companion object {

        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            SkinResources()
        }

        fun init(context: Context): SkinResources {
            instance.context = context
            return instance
        }

    }

    fun getColor(color: Int): Int {
        val skinRes = getIdentifier(color)
        if (isDefaultSkin || skinRes == 0) {
            return context.getColor(color)
        }
        return skinResources!!.getColor(skinRes, null)
    }

    fun getColorStateList(color: Int): ColorStateList {
        val skinRes = getIdentifier(color)
        if (isDefaultSkin || skinRes == 0) {
            return context.getColorStateList(color)
        }
        return skinResources!!.getColorStateList(skinRes, null)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun getDrawable(resId: Int): Drawable? {
        val skinRes = getIdentifier(resId)
        if (isDefaultSkin || skinRes == 0) {
            return context.getDrawable(resId)
        }
        return skinResources!!.getDrawable(skinRes, null)
    }

    fun getBackground(resId: Int): Any {
        val resTypeName = context.resources.getResourceTypeName(resId)

        return if ("color" == resTypeName) {
            getColor(resId)
        } else {
            getDrawable(resId) as Drawable
        }
    }

    fun applySkin(resources: Resources, pkgName: String) {
        skinResources = resources
        skinPkgName = pkgName
        isDefaultSkin = pkgName.isEmpty()
    }

    /**
     * 获取标识符
     */
    fun getIdentifier(resId: Int): Int {
        if (isDefaultSkin) {
            return resId
        }
        val entryName = context.resources.getResourceEntryName(resId)
        val typeName = context.resources.getResourceTypeName(resId)
        Logger.d("entryName -> $entryName")
        Logger.d("typeName -> $typeName")
        Logger.d("skinPkgName -> $skinPkgName")
        return skinResources!!.getIdentifier(entryName, typeName, skinPkgName)
    }

    fun reset() {
        skinResources = null
        skinPkgName = ConstantUtils.EMPTY
        isDefaultSkin = true
    }

}