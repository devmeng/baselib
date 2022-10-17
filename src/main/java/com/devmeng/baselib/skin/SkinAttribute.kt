package com.devmeng.baselib.skin

import android.util.AttributeSet
import android.view.View
import com.devmeng.baselib.skin.entity.SkinPair
import com.devmeng.baselib.skin.entity.SkinView
import com.devmeng.baselib.skin.utils.SkinThemeUtils

/**
 * Created by Richard
 * Version : 1
 * Description :
 * 更换皮肤时所需更换的属性
 *
 * 注意: attributeList 在增加属性个体时需要对 @see #SkinView 中的 applySkin() 方法的 switch 增加 case
 */
class SkinAttribute {

    private val attributeList = mutableListOf<String>()
    private val skinViews = mutableListOf<SkinView>()

    init {
        attributeList.add("background")
        attributeList.add("backgroundTint")
        attributeList.add("src")
        attributeList.add("textColor")
        attributeList.add("tint")
        attributeList.add("drawableLeft")
        attributeList.add("drawableStart")
        attributeList.add("drawableRight")
        attributeList.add("drawableEnd")
        attributeList.add("drawableTop")
        attributeList.add("drawableBottom")
        attributeList.add("drawableLeftCompat")
        attributeList.add("drawableStartCompat")
        attributeList.add("drawableRightCompat")
        attributeList.add("drawableEndCompat")
        attributeList.add("drawableTopCompat")
        attributeList.add("drawableBottomCompat")
        attributeList.add("drawableTint")
        /** 注意: attributeList 在增加属性个体时需要对
        #SkinView 中的 applySkin() 方法的 switch 增加 case
         */
    }

    fun load(view: View, attrs: AttributeSet) {
        val skinPairList = mutableListOf<SkinPair>()
        for (index in 0 until attrs.attributeCount) {
            val attrName = attrs.getAttributeName(index)
            if (attributeList.contains(attrName)) {
                val attrValue = attrs.getAttributeValue(index)
                if (attrValue.startsWith("#")) {
                    //忽略值设置为"#"开头的属性
                    continue
                }
                //此处以 @resId 为场景截取 resourceId  例如: color="@color/color_black"
                var resId = Integer.parseInt(attrValue.substring(1))
                if (attrValue.startsWith("?")) {
                    //截取并获取属性ID, 通常为 Theme 中定义的属性 例如: color="?colorAccent"
                    val attrId = Integer.parseInt(attrValue.substring(1))
                    resId = SkinThemeUtils.getResId(view.context, intArrayOf(attrId))[0]
                }
                skinPairList.add(
                    SkinPair(
                        attrName,
                        resId
                    )
                )
            }
        }

        //应用并缓存 皮肤及view
        if (skinPairList.isNotEmpty()) {
            val skinView =
                SkinView(view, skinPairList)
            skinView.applySkin()
            skinViews.add(skinView)
        }
    }

    fun applySkin() {
        for (skinView in skinViews) {
            skinView.applySkin()
        }
    }


}