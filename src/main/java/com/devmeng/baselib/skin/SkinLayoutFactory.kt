package com.devmeng.baselib.skin

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import com.devmeng.baselib.utils.Logger
import java.lang.reflect.Constructor
import java.util.*

/**
 * Created by Richard
 * Version : 1
 * Description :
 */
class SkinLayoutFactory : LayoutInflater.Factory2, Observer {

    private val viewConMap: HashMap<String, Constructor<out View>> = hashMapOf()

    private val viewPackList: MutableList<String> = mutableListOf(
        "android.widget.",
        "android.view.",
        "android.webkit.",
        /*"android.gesture.",
        "android.app.",
        "android.appwidget.",
        "android.inputmethodservice.",
        "android.opengl.",
        "android.media.tv.",*/
    )

    private val constructorSignature = arrayOf(
        Context::class.java, AttributeSet::class.java
    )

    private val skinAttribute = SkinAttribute()

    override fun onCreateView(
        parent: View?,
        name: String,
        context: Context,
        attrs: AttributeSet
    ): View {
        val view = createViewFromTag(name, context, attrs) ?: return createView(
            name,
            context,
            attrs
        )!!
        skinAttribute.load(view, attrs)
        return view
    }

    private fun createViewFromTag(name: String, context: Context, attrs: AttributeSet): View? {
        Logger.d("SkinLayoutFactory -> view name: $name")
        var view: View? = null
        if (-1 == name.indexOf(".")) {
            for (pack in viewPackList) {
                view = createView(pack + name, context, attrs)
                if (null != view) {
                    break
                }
            }
            return view
        }
        return createView(name, context, attrs)
    }

    private fun createView(name: String, context: Context, attrs: AttributeSet): View? {
        var constructor: Constructor<out View>? = viewConMap[name]
        if (null == constructor) {
            try {
                val aClass: Class<out View?> = context.classLoader.loadClass(name).asSubclass(
                    View::class.java
                )
                constructor = aClass.getConstructor(*constructorSignature)
                viewConMap[name] = constructor
            } catch (e: Exception) {
                return null
            }
        }
        return constructor!!.newInstance(context, attrs)
    }

    override fun onCreateView(name: String, context: Context, attrs: AttributeSet): View? {
        return null
    }

    override fun update(o: Observable?, arg: Any?) {
        skinAttribute.applySkin()
    }
}