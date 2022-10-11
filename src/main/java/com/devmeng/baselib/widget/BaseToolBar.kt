package com.devmeng.baselib.widget

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.util.TypedValue
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import androidx.annotation.ColorRes
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.devmeng.baselib.R
import com.devmeng.baselib.utils.ConstantUtils
import com.devmeng.baselib.utils.Logger

/**
 * Created by Richard
 * Version : 1
 * Description : 基准 ToolBar 控件
 * 包含返回按钮（返回类型：backIconRes 分为 back,close，图标可根据需求更改）
 * 标题（可控制标题的内部 Gravity）
 * 尾部工具（图标可根据需求更改，可做弹窗等）
 *
 * 自定义属性:
 * isEndToolAvailable: 尾部工具是否可用（可用就显示）
 * backIconRes: 返回按钮 Icon 资源
 * titleText: 标题文本
 * titleTextSize: 标题文本尺寸
 * titleGravity: 标题文本内部对齐方式
 * titleTextColor: 标题文本颜色
 * titlePaddingHorizontal: 文本横向内边距
 * endIconRes: 尾部工具 Icon 资源
 * endIconTint: 尾部工具 Icon 资源渲染
 *
 * 公开属性:
 * imgBackSize: 返回按钮尺寸(单位 dp)
 * imgToolSize: 尾部按钮尺寸(单位 dp)
 */
class BaseToolBar @JvmOverloads constructor(
    context: Context,
    var attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : LinearLayoutCompat(context, attrs, defStyleAttr), View.OnClickListener {

    private val imgBackClose: AppCompatImageView = AppCompatImageView(context)
    private val tvTitle: AppCompatTextView = AppCompatTextView(context)
    private val imgEndTool: AppCompatImageView = AppCompatImageView(context)

    //尾部工具是否可用
    private var isEndToolAvailable = true

    //自定义属性
    //返回类型
    private var backIconRes: Int = 0

    //标题文本
    private var titleText: String = ConstantUtils.EMPTY

    //标题文本尺寸
    private var titleTextSize = 16F

    //标题文本颜色
    private var titleTextColor: Int = getColor(R.color.color_black_333)

    //尾部工具图标
    private var endIconRes: Int = 0

    //尾部工具图标渲染
    private var endIconTint: Int = getColor(R.color.color_black_333)

    //文本内部对齐方式
    private var titleGravity: Int = Gravity.START

    //文本内边距
    private var titlePaddingHorizontal: Int = 0

    private var widgetWidth = 0
    private var widgetHeight = 0

    var imgBackSize = 30
    var imgToolSize = 30

    init {
        initConfig()
    }

    private fun initConfig() {
        val typeAttrs = context.obtainStyledAttributes(attrs, R.styleable.BaseToolBar)
        with(typeAttrs) {
            backIconRes = getResourceId(R.styleable.BaseToolBar_backIconRes, backIconRes)
            titleText = getString(R.styleable.BaseToolBar_titleText).toString()
            titleTextSize = getDimension(R.styleable.BaseToolBar_titleTextSize, titleTextSize)
            titleTextColor =
                getColor(R.styleable.BaseToolBar_titleTextColor, titleTextColor)
            endIconRes =
                getResourceId(R.styleable.BaseToolBar_endIconRes, endIconRes)
            endIconTint = getColor(R.styleable.BaseToolBar_endIconTint, endIconTint)
            titleGravity = getInteger(R.styleable.BaseToolBar_titleGravity, titleGravity)
            titlePaddingHorizontal =
                getDimensionPixelOffset(
                    R.styleable.BaseToolBar_titlePaddingHorizontal,
                    titlePaddingHorizontal
                )


            recycle()
        }

        imgBackClose.setImageResource(backIconRes)
        tvTitle.text = titleText
        tvTitle.setTextColor(titleTextColor)
        tvTitle.setTextSize(TypedValue.COMPLEX_UNIT_PX, titleTextSize)
        tvTitle.setPadding(titlePaddingHorizontal, 0, titlePaddingHorizontal, 0)
        tvTitle.gravity = titleGravity.or(Gravity.CENTER_VERTICAL)
        imgEndTool.visibility = GONE
        imgBackClose.setOnClickListener(this)
    }


    @SuppressLint("DrawAllocation")
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)

        if (widthMode == MeasureSpec.EXACTLY) {
            widgetWidth = widthSize
            widgetHeight = if (heightMode == MeasureSpec.EXACTLY) {
                heightSize
            } else {
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    60f,
                    context.resources.displayMetrics
                ).toInt()
            }
        } else {
            widgetWidth = context.resources.displayMetrics.widthPixels
            widgetHeight = if (heightMode == MeasureSpec.EXACTLY) {
                heightSize
            } else {
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    60f,
                    context.resources.displayMetrics
                ).toInt()
            }
        }

        imgBackClose.layoutParams =
            LayoutParams(
                TypedValue.applyDimension(
                    TypedValue.COMPLEX_UNIT_DIP,
                    imgBackSize.toFloat(),
                    context.resources.displayMetrics
                ).toInt(), widgetHeight
            )
        tvTitle.layoutParams = LayoutParams(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            widgetHeight, 1f
        )

        //添加控件
        removeAllViews()
        addView(imgBackClose)
        addView(tvTitle)
        if (isEndToolAvailable || endIconRes != 0) {
            imgEndTool.visibility = VISIBLE
            imgEndTool.layoutParams =
                LayoutParams(
                    TypedValue.applyDimension(
                        TypedValue.COMPLEX_UNIT_DIP,
                        imgToolSize.toFloat(),
                        context.resources.displayMetrics
                    ).toInt(), ViewGroup.LayoutParams.MATCH_PARENT
                )
            imgEndTool.imageTintList = ColorStateList.valueOf(endIconTint)
            imgEndTool.setImageResource(endIconRes)
            imgEndTool.setOnClickListener(this)
            addView(imgEndTool)
        }

        setMeasuredDimension(widgetWidth, widgetHeight)
    }

    override fun onClick(v: View?) {
        if (v == imgBackClose) {
            val activity = context as Activity
            activity.finish()
        } else if (v == imgEndTool) {
            endToolCapabilities()
            Logger.d("尾部工具")
        }
    }

    /**
     * 尾部控件的功能
     */
    private fun endToolCapabilities() {

    }

    private fun getColor(@ColorRes colorRes: Int): Int {
        return context.getColor(colorRes)
    }

}