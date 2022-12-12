package com.devmeng.baselib.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.util.AttributeSet
import android.view.Gravity.CENTER
import android.view.MotionEvent
import android.view.ViewGroup
import android.view.Window
import android.view.animation.AlphaAnimation
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.devmeng.baselib.R
import com.devmeng.baselib.normal.*
import com.devmeng.baselib.skin.SkinAttribute
import com.devmeng.baselib.skin.entity.SkinPair
import com.devmeng.baselib.skin.utils.SkinResources
import com.devmeng.baselib.utils.Logger
import com.devmeng.baselib.utils.LottieBuilder
import java.lang.ref.WeakReference

/**
 * Created by Richard -> MHS
 * Date : 2022/10/31  18:56
 * Version : 1
 */
const val DEFAULT_LOAD_ANIM_FOLDER = "load"
const val DEFAULT_LOAD_ANIM_IF_HAD_IMAGE = false

@SuppressLint("ResourceAsColor")
class LoadingView(
    context: Context,
    defAttrs: AttributeSet? = null
) : ConstraintLayout(context, defAttrs) {

    private val pairList = mutableListOf<SkinPair>()
    private val attrsList: MutableList<String> = mutableListOf(
        "loadAnimUrl",
        "loadAnimFolderName",
        "loadAnimIsHadImage",
        "loadText",
        "loadBackColorRes",
        "loadTextColorRes",
        "loadTextSize",
        "loadBackBorderWidth",
        "loadBackBorderColorRes",
        "loadBackShadeColorRes",
    )


    private lateinit var cornerBack: CornerShadowLayout
    private lateinit var lottieAnim: LottieAnimationView
    private lateinit var tvLoading: TextView

    //    private val cornerBack = CornerShadowLayout(context)
//    private val lottieAnim = LottieAnimationView(context)
//    private val tvLoading = TextView(context)
    private val activity = WeakReference(context as? Activity).get()!!
    private val cacheActMap = hashMapOf<Activity, ViewGroup>()

    var loadBackWidth = toDp(context, 200f)
    var loadBackHeight = toDp(context, 200f)
    var loadAnimWidth = toDp(context, 150f)
    var loadAnimHeight = toDp(context, 150f)
    var loadBackColorRes = R.color.load_view_back_color
    var loadTextColorRes = R.color.load_view_tv_color
    var loadBackBorderWidth = 0F
    var loadBackBorderColorRes = loadBackColorRes
    var loadBackShadeColorRes = R.color.color_trans_30

    private var loadTextColor = context.getColor(loadTextColorRes)
    private var loadBackColor = context.getColor(loadBackColorRes)
    private var loadBackShadeColor = context.getColor(loadBackShadeColorRes)
    private var loadBackBorderColor = context.getColor(loadBackBorderColorRes)

    var loadAnimUrl = "https://assets2.lottiefiles.com/packages/lf20_usmfx6bp.json"
    var loadAnimFolderName = DEFAULT_LOAD_ANIM_FOLDER
    var loadAnimIsHadImage = DEFAULT_LOAD_ANIM_IF_HAD_IMAGE
    var loadText = context.getString(R.string.string_loading)
    var loadTextSize = 16f

    fun showLoading(container: ViewGroup = activity.window.decorView.findViewById(Window.ID_ANDROID_CONTENT)) {
        this.removeAllViews()

        cornerBack = CornerShadowLayout(context)
        lottieAnim = LottieAnimationView(context)
        tvLoading = TextView(context)

        cornerBack.apply {
            mWidth = loadBackWidth
            mHeight = loadBackHeight
//            borderColor = context.getColor(loadBackBorderColor)
            borderWidth = loadBackBorderWidth
            allCornerRadius = toDp(context, 10f).toFloat()
            backColor = loadBackColor
            shadowColor = loadBackShadeColor
            shadowRadius = toDp(context, 10f).toFloat()
            update()
        }

        try {
            lottieAnim.apply {
                LottieBuilder.builder()?.apply {
                    with(context)
                    lottie(lottieAnim)
                    repeatCount(LottieDrawable.INFINITE)
                    if (loadAnimUrl.isEmpty()) {
                        lottieAssetAnim(loadAnimFolderName, loadAnimIsHadImage)
                    } else {
                        lottieFromUrl(loadAnimUrl)
                    }
                    play()
                }
            }
        } catch (e: Exception) {
            Logger.e(e.stackTraceToString())
            lottieAnim.visibility = GONE
        }

        tvLoading.apply {
            text = loadText
            setTextColor(loadTextColor)
            textSize = loadTextSize
        }

        addView(cornerBack, lp(loadBackWidth, loadBackHeight))
        addView(lottieAnim, lp(loadAnimWidth, loadAnimHeight))
        addView(tvLoading, wrapper())

        show(container)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        for (index in 0 until childCount) {
            val child = getChildAt(index)

            val l = measuredWidth / 2 - child.measuredWidth / 2
            var t = measuredHeight / 2 - child.measuredHeight / 2

            when (child) {
                is LottieAnimationView -> {
                    val offset = cornerBack.shadowRadius * 2
                    t = (cornerBack.top + offset).toInt()
                    child.layout(
                        l,
                        t,
                        l + child.measuredWidth,
                        t + child.measuredHeight
                    )
                }
                is TextView -> {
                    t = lottieAnim.bottom
                    child.layout(
                        l,
                        lottieAnim.bottom,
                        l + child.measuredWidth,
                        t + child.measuredHeight
                    )
                }
                else -> {
                    child.layout(
                        l,
                        t,
                        l + child.measuredWidth,
                        t + child.measuredHeight
                    )
                }
            }

        }
    }

    private fun show(container: ViewGroup = activity.window.decorView.findViewById(Window.ID_ANDROID_CONTENT)) {
        val lp =
            FrameLayout.LayoutParams(
                context.resources.displayMetrics.widthPixels,
                context.resources.displayMetrics.heightPixels,
                CENTER
            )
        val viewGroup = cacheActMap[activity]
            ?: container
        viewGroup.apply {
            removeView(this@LoadingView)
            showAnim()
            addView(this@LoadingView, lp)
            cacheActMap[activity] = this
        }

    }

    fun dismissLoad() {
        val contentViewGroup = cacheActMap[activity]
        contentViewGroup?.apply {
            dismissAnim()
            removeView(this@LoadingView)
        }
    }

    private fun showAnim() {
        val alphaAnim = AlphaAnimation(0f, 1f)
        alphaAnim.duration = 200
        alphaAnim.start()
        this.animation = alphaAnim
    }

    private fun dismissAnim() {
        val alphaAnim = AlphaAnimation(1f, 0f)
        alphaAnim.duration = 200
        alphaAnim.start()
        this.animation = alphaAnim
    }

    /**
     * 在自主创建的控件中 使用皮肤
     */
    fun applySkin() {
        if (pairList.isNotEmpty()) {
            return
        }
        pairList.addAll(SkinAttribute.instance.reflectSkinPair(this, attrsList))
        getSkinResources(SkinResources.instance)
    }

    private fun getSkinResources(resources: SkinResources) {
        for ((attrName, resId) in pairList) {
            when (attrName) {
                "loadAnimUrl" -> {}
                "loadAnimFolderName" -> {}
                "loadAnimIsHadImage" -> {}
                "loadText" -> {}
                "loadBackColorRes" -> {
                    loadBackColor = resources.getColor(resId)
                }
                "loadTextColorRes" -> {
                    loadTextColor = resources.getColor(resId)
                }
                "loadTextSize" -> {}
                "loadBackBorderWidth" -> {}
                "loadBackBorderColorRes" -> {
                    loadBackBorderColor = resources.getColor(resId)
                }
                "loadBackShadeColorRes" -> {
                    loadBackShadeColor = resources.getColor(resId)
                }
            }
        }
//        showLoading(cacheActMap[activity]!!)
    }

    fun resetSkin() {
        getSkinResources(SkinResources.instance)
        pairList.removeAll(pairList)
//        showLoading(cacheActMap[activity]!!)
    }

    @SuppressLint("ClickableViewAccessibility")
    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return true
    }

    init {
        /*val typedArray =
            context.obtainStyledAttributes(defaultAttrs, R.styleable.LoadingView)
        typedArray.apply {
            //加载背景宽度
            loadBackWidth = getDimension(R.styleable.LoadingView_loadBackWidth, loadBackWidth)
            //加载背景高度
            loadBackHeight = getDimension(R.styleable.LoadingView_loadBackHeight, loadBackHeight)
            //加载动画路径
//            loadAnimPath = getString(R.styleable.LoadingView_loadAnimPath)!!
            //加载动画尺寸
            loadAnimSize = getDimension(R.styleable.LoadingView_loadAnimSize, loadAnimSize)
            //加载文本
//            loadText = getString(R.styleable.LoadingView_loadText)!!
            //按钮文本
//            loadClickText = getString(R.styleable.LoadingView_loadClickText)!!
            //加载文本颜色
            loadTextColor = getColor(R.styleable.LoadingView_loadTextColor, loadTextColor)
            //按钮文本颜色
            loadClickTextColor =
                getColor(R.styleable.LoadingView_loadClickTextColor, loadClickTextColor)
            //加载文本尺寸
            loadTextSize = getDimension(R.styleable.LoadingView_loadTextSize, loadTextSize)
            //加载文本尺寸
            loadClickTextSize =
                getDimension(R.styleable.LoadingView_loadClickTextSize, loadClickTextSize)
            recycle()
        }*/
    }

    /*@SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize
            if (heightMode == MeasureSpec.EXACTLY) {
                mHeight = heightSize
            } else if (heightMode == MeasureSpec.AT_MOST) {
                mHeight = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    100f,
                    context.resources.displayMetrics
                ).toInt()
            }
        } else if (widthMode == MeasureSpec.AT_MOST) {
            mWidth = TypedValue.applyDimension(
                TypedValue.COMPLEX_UNIT_DIP,
                100f,
                context.resources.displayMetrics
            ).toInt()
            if (heightMode == MeasureSpec.EXACTLY) {
                mHeight = heightSize
            } else if (heightMode == MeasureSpec.AT_MOST) {
                mHeight = TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    100f,
                    context.resources.displayMetrics
                ).toInt()
            }
        }

        setMeasuredDimension(mWidth, mHeight)
    }*/

}