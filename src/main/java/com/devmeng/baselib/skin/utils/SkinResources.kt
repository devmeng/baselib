package com.devmeng.baselib.skin.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import com.devmeng.baselib.utils.EMPTY
import com.devmeng.baselib.utils.Logger

/**
 * Created by Richard
 * Version : 1
 * Description :
 */
class SkinResources private constructor() {

    lateinit var context: Context
    private var skinResources: Resources? = null
    private var skinPkgName: String = EMPTY
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

    fun getDimension(dimens: Int): Float {
        val skinRes = getIdentifier(dimens)
        if (isDefaultSkin.or(skinRes == 0)) {
            return context.resources.getDimension(dimens)
        }
        return skinResources!!.getDimension(skinRes)
    }

    fun getColor(color: Int): Int {
        val skinRes = getIdentifier(color)
        if (isDefaultSkin.or(skinRes == 0)) {
            return context.getColor(color)
        }
        return skinResources!!.getColor(skinRes, null)
    }

    fun getColorId(color: Int): Int {
        val skinRes = getIdentifier(color)
        if (isDefaultSkin.or(skinRes == 0)) {
            return color
        }
        return skinRes
    }

    fun getColorStateList(color: Int): ColorStateList? {
        val skinRes = getIdentifier(color)
        if (isDefaultSkin.or(skinRes == 0)) {
            return context.getColorStateList(color)
        }
        return skinResources?.getColorStateList(skinRes, null)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun getDrawable(resId: Int): Drawable? {
        val skinRes = getIdentifier(resId)
        if (isDefaultSkin.or(skinRes == 0)) {
            return context.getDrawable(resId)
        }
        return skinResources!!.getDrawable(skinRes, null)
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    fun getDrawableId(resId: Int): Int {
        val skinRes = getIdentifier(resId)
        if (isDefaultSkin.or(skinRes == 0)) {
            return resId
        }
        return skinRes
    }

    fun getBackground(resId: Int): Any? {
        val resTypeName = context.resources.getResourceTypeName(resId)

        return if ("color" == resTypeName) {
            getColor(resId)
        } else {
            getDrawable(resId)
        }
    }

    fun getBoolean(resId: Int): Boolean {
        val skinRes = getIdentifier(resId)
        if (isDefaultSkin.or(skinRes == 0)) {
            return context.resources.getBoolean(resId)
        }
        return skinResources!!.getBoolean(skinRes)
    }

    fun getTypeface(typefaceId: Int): Typeface {
        val typefacePath = getSkinString(typefaceId)
        if (typefacePath.isEmpty()) {
            return Typeface.DEFAULT
        }
        try {
            if (isDefaultSkin) {
                //???????????????????????????????????????????????????????????????
                // ?????????app -> value.xml / value-night.xml ??? skin_ttf_* ?????????????????????
                return Typeface.createFromAsset(context.resources.assets, typefacePath)
            }
            return Typeface.createFromAsset(skinResources?.assets, typefacePath)
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return Typeface.DEFAULT
    }

    private fun getSkinString(skinTypeFaceId: Int): String {
        if (skinTypeFaceId == 0) {
            throw Resources.NotFoundException("???????????????????????????????????? attrs.xml ????????? skinTypeface ????????????????????????")
        }
        if (isDefaultSkin) {
            return context.resources.getString(skinTypeFaceId)
        }
        val resId = getIdentifier(skinTypeFaceId)
        if (resId == 0) {
            return context.resources.getString(skinTypeFaceId)
        }
        return skinResources?.getString(resId)!!
    }

    /**
     * ????????????
     * @param resources ??????????????? Resources
     * @param pkgName ?????????????????? package
     * @see com.devmeng.baselib.skin.SkinManager.loadSkin
     */
    fun applySkin(resources: Resources, pkgName: String) {
        skinResources = resources
        skinPkgName = pkgName
        isDefaultSkin = pkgName.isEmpty()
    }

    /**
     * ???????????????
     * @param resId ??????????????????????????????????????? id ???: @drawable/icon ????????? id: Int
     */
    fun getIdentifier(resId: Int): Int {
        if (isDefaultSkin) {
            return resId
        }
        //??????: @drawable/icon -> drawable
//        val resName = context.resources.getResourceName(resId)
        val typeName = context.resources.getResourceTypeName(resId)
        //??????: @drawable/icon -> icon
        val entryName = context.resources.getResourceEntryName(resId)
//        Logger.d("resName -> $resName")
        Logger.d("typeName -> $typeName")
        Logger.d("entryName -> $entryName")
//        Logger.d("skinPkgName -> $skinPkgName")
        val skinIdentifier = skinResources?.getIdentifier(entryName, typeName, skinPkgName)
        Logger.d("skinIdentifier -> $skinIdentifier")
        return skinIdentifier!!
    }

    fun reset() {
        skinResources = null
        skinPkgName = EMPTY
        isDefaultSkin = true
    }

}