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
 */
public class SkinView {

    public View view;
    public List<SkinPair> pairList;

    public SkinView(View view, List<SkinPair> pairList) {
        this.view = view;
        this.pairList = pairList;
    }

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
