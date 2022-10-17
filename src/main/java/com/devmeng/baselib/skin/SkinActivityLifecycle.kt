package com.devmeng.baselib.skin

import android.annotation.SuppressLint
import android.app.Activity
import android.app.Application
import android.os.Bundle
import android.view.LayoutInflater

/**
 * Created by Richard
 * Version : 1
 * Description :
 */
class SkinActivityLifecycle : Application.ActivityLifecycleCallbacks {

    private val factoryMap: HashMap<Activity, SkinLayoutFactory> = hashMapOf()

    @SuppressLint("DiscouragedPrivateApi")
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {

        val layoutInflater = LayoutInflater.from(activity)
        try {
            val field = LayoutInflater::class.java.getDeclaredField("mFactorySet")
            field.isAccessible = true
            field.setBoolean(layoutInflater, false)

        } catch (e: Exception) {
            e.printStackTrace()
        }
        val factory = SkinLayoutFactory()
        layoutInflater.factory2 = factory
        //注册观察者
        SkinManager.instance.addObserver(factory)
        factoryMap[activity] = factory
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {

    }

    override fun onActivityPaused(activity: Activity) {

    }

    override fun onActivityStopped(activity: Activity) {

    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {

    }

    override fun onActivityDestroyed(activity: Activity) {
        //删除观察者
        val factory = factoryMap.remove(activity)
        SkinManager.instance.deleteObserver(factory)
    }
}