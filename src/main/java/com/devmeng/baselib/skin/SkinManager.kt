package com.devmeng.baselib.skin

import android.annotation.SuppressLint
import android.app.Application
import android.content.pm.PackageManager
import android.content.res.AssetManager
import android.content.res.Resources
import com.devmeng.baselib.skin.utils.SkinPreference
import com.devmeng.baselib.skin.utils.SkinResources
import com.devmeng.baselib.utils.ConstantUtils
import com.devmeng.baselib.utils.Logger
import java.util.*

/**
 * Created by Richard
 * Version : 1
 * Description :
 */
class SkinManager : Observable() {

    private lateinit var application: Application

    companion object {

        val instance by lazy(LazyThreadSafetyMode.SYNCHRONIZED) {
            SkinManager()
        }

        fun init(application: Application): SkinManager {
            application.registerActivityLifecycleCallbacks(SkinActivityLifecycle())
            SkinPreference.init(application.applicationContext)
            SkinResources.init(application.applicationContext)
            Logger.d("skinResource context -> ${SkinResources.init(application.applicationContext).context}")
            instance.application = application
            return instance
        }
    }

    @SuppressLint("PrivateApi", "DiscouragedPrivateApi", "SoonBlockedPrivateApi")
    fun loadSkin(skinPath: String = ConstantUtils.EMPTY) {
        if (skinPath.isNotEmpty()) {
            try {
                Logger.d("skinPath -> $skinPath")
                val assetManager = AssetManager::class.java.newInstance()
                /*val method =
                    assetManager.javaClass.getMethod(
                        "addAssetPathInternal",
                        String::class.java,
                        Boolean::class.java,
                        Boolean::class.java
                    )*/
                val method = assetManager.javaClass.getMethod(
                    "addAssetPath",
                    String::class.java
                )
//                method.invoke(assetManager, skinPath, false, false)
                method.invoke(assetManager, skinPath)
                val resources = application.resources
                //通过反射获取 ResourceImpl 并将 mAssets 等变量赋值
                /*
                val resourcesImpl =
                    Resources::class.java.classLoader!!
                        .loadClass("android.content.res.ResourcesImpl")

                val assets = resourcesImpl.getDeclaredField("mAssets")
                val metrics = resourcesImpl.getDeclaredField("mMetrics")
                val config = resourcesImpl.getDeclaredField("mConfiguration")
                metrics.isAccessible = true
                config.isAccessible = true

                assets.set(resourcesImpl, assetManager)
                metrics.set(resourcesImpl, resources.displayMetrics)
                config.set(resourcesImpl, resources.configuration)

                val mResourcesImpl = Resources::class.java.getField("mResourcesImpl")

                mResourcesImpl.set(resources, resourcesImpl)
*/
                val skinResources =
                    Resources(assetManager, resources.displayMetrics, resources.configuration)
                //存储皮肤信息
                SkinPreference.instance.setSkin(skinPath)

                //获取 skinPath 所在的 Apk 包名
                val pkgManager = application.packageManager
                val packageArchiveInfo =
                    pkgManager.getPackageArchiveInfo(skinPath, PackageManager.GET_ACTIVITIES)
                val pkgName = packageArchiveInfo!!.packageName
                SkinResources.instance.applySkin(skinResources, pkgName)

            } catch (e: Exception) {
                e.printStackTrace()
            }
        } else {
            //还原皮肤
            SkinPreference.instance.setSkin()
            SkinResources.instance.reset()
        }
        setChanged()
        notifyObservers()
    }

}