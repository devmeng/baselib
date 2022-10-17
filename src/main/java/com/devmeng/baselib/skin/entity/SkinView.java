package com.devmeng.baselib.skin.entity;

import android.content.res.ColorStateList;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.core.view.ViewCompat;
import androidx.core.widget.TextViewCompat;

import com.devmeng.baselib.skin.utils.SkinResources;

import java.util.List;

/**
 * Created by Richard
 * Version : 1
 * Description :
 * 需要换肤的 View
 * 换肤所需的属性、属性值 pairList
 *
 * @see #pairList
 * 应用皮肤: 遍历 pairList 获取换肤使用的属性及属性值
 * 并与 SkinAttribute#attributeList 中的元素一一对应
 * @see #applySkin()
 * @see com.devmeng.baselib.skin.SkinAttribute
 * <p>
 * 换肤时通过 SkinResources 获取
 * 注: 如 SkinAttribute#attributeList 增加元素，
 * 则需要在 applySkin 方法的 switch 语句中增加方案 case
 */
public class SkinView {

    public View view;
    public List<SkinPair> pairList;

    public SkinView(View view, List<SkinPair> pairList) {
        this.view = view;
        this.pairList = pairList;
    }

    /**
     * 应用皮肤
     * 1.遍历 #pairList 获取换肤属性及其换肤前的属性值
     * 2.由 SkinResources 对象获取皮肤属性值
     * 皮肤属性对应 SkinAttribute.attributeList 中的元素
     *
     * @see com.devmeng.baselib.skin.SkinAttribute
     * 注: 如 SkinAttribute#attributeList 增加元素，
     * 则需要在 switch 语句中增加方案 case 并从 SkinResources 中获取对应资源
     */
    public void applySkin() {
        for (SkinPair skinPair : pairList) {
            String attrName = skinPair.attrName;
            int resId = skinPair.resId;
            SkinResources skinResources = SkinResources.Companion.init(view.getContext().getApplicationContext());
            Drawable top = null;
            Drawable bottom = null;
            Drawable left = null;
            Drawable right = null;
            switch (attrName) {
                case "background":
                    Object background = skinResources.getBackground(resId);

                    if (background instanceof Integer) {
                        view.setBackgroundColor((Integer) background);
                    } else {
                        ViewCompat.setBackground(view, (Drawable) background);
                    }
                    break;
                case "backgroundTint":
                    ColorStateList colorStateList = skinResources.getColorStateList(resId);
                    view.setBackgroundTintList(colorStateList);
                    break;
                case "src":
                    Drawable drawable = skinResources.getDrawable(resId);
                    ((ImageView) view).setImageDrawable(drawable);
                    break;
                case "textColor":
                    ((TextView) view).setTextColor(skinResources.getColorStateList(resId));
                    break;
                case "tint":
                    ((ImageView) view).setImageTintList(skinResources.getColorStateList(resId));
                    break;

                case "drawableLeft":
                case "drawableLeftCompat":
                case "drawableStart":
                case "drawableStartCompat":
                    left = skinResources.getDrawable(resId);
                    break;
                case "drawableRight":
                case "drawableRightCompat":
                case "drawableEnd":
                case "drawableEndCompat":
                    right = skinResources.getDrawable(resId);
                    break;
                case "drawableTop":
                case "drawableTopCompat":
                    top = skinResources.getDrawable(resId);
                    break;
                case "drawableBottom":
                case "drawableBottomCompat":
                    bottom = skinResources.getDrawable(resId);
                    break;
                case "drawableTint":
                    TextViewCompat.setCompoundDrawableTintList((TextView) view, skinResources.getColorStateList(resId));
                    break;
            }
            if (view instanceof TextView) {
                ((TextView) view).setCompoundDrawablesRelativeWithIntrinsicBounds(left, top, right, bottom);
            }
        }
    }

}
