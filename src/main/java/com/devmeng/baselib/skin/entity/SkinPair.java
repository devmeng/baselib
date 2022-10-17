package com.devmeng.baselib.skin.entity;

/**
 * Created by Richard
 * Version : 1
 * Description :
 * 用于缓存换肤所需的属性及其属性值
 */
public class SkinPair {

    public String attrName;
    public int resId;

    public SkinPair(String attrName, int resId) {
        this.attrName = attrName;
        this.resId = resId;
    }
}
