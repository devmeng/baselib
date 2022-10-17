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

    /**
     * 应用皮肤
     * @param resources 客制化皮肤 Resources
     * @param pkgName 皮肤包所在的 package
     * @see com.devmeng.baselib.skin.SkinManager.loadSkin
     */
    fun applySkin(resources: Resources, pkgName: String) {
        skinResources = resources
        skinPkgName = pkgName
        isDefaultSkin = pkgName.isEmpty()
    }

    /**
     * 获取标识符
     * @param resId 皮肤包中皮肤属性对应的资源 id 例: @drawable/icon 对应的 id: Int
     */
    fun getIdentifier(resId: Int): Int {
        if (isDefaultSkin) {
            return resId
        }
        //例如: @drawable/icon -> drawable
        val resName = context.resources.getResourceName(resId)
        val typeName = context.resources.getResourceTypeName(resId)
        //例如: @drawable/icon -> icon
        val entryName = context.resources.getResourceEntryName(resId)
        Logger.d("resName -> $resName")
        Logger.d("typeName -> $typeName")
        Logger.d("entryName -> $entryName")
        Logger.d("skinPkgName -> $skinPkgName")
        return skinResources!!.getIdentifier(entryName, typeName, skinPkgName)
    }

    fun reset() {
        skinResources = null
        skinPkgName = ConstantUtils.EMPTY
        isDefaultSkin = true
    }

}