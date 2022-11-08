package com.devmeng.baselib.utils

import android.animation.Animator
import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import android.graphics.drawable.Drawable
import android.text.TextUtils
import android.view.animation.Animation
import androidx.annotation.DrawableRes
import androidx.annotation.RawRes
import androidx.core.content.ContextCompat
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.model.KeyPath
import com.devmeng.baselib.R
import java.io.File

/**
 * FileName: LottieBuilder
 * Author: 孟海粟
 * Date: 2021/6/19 16:02
 * Description: Lottie 动画构造器
 * 我想起来就更新系列
 */
object LottieBuilder {
    const val DIR_LOTTIE = "lotties"

    @SuppressLint("StaticFieldLeak")
    private var sBuilder: Builder? = null
    fun builder(): Builder? {
        if (sBuilder == null) {
            sBuilder = Builder()
        }
        return sBuilder
    }

    class Builder {
        private val mImageAssets: String? = null

        @Deprecated("使用 lottieAssetAnim 替代，下方弃用的三个方法")
        private var mAssetsFolderName: String? = null
        private var mContext: Context? = null
        private var mLottie: LottieAnimationView? = null
        private val mAnimationFromUrl: String? = null

        /**
         * 必要方法 在一开始执行
         *
         * @param context
         * @return
         */
        fun with(context: Context?): Builder =
            this.also {
                mContext = context
            }

        /**
         * 配置 LottieAnimationView
         *
         * @param lottie LottieAnimationView
         * @return [Builder]
         */
        fun lottie(lottie: LottieAnimationView?): Builder = this.also { mLottie = lottie }

        /**
         * 设置 lottie 下的动画资源根目录名称
         * 建议存放 assets/lottie/动画目录
         *
         * @param folder 目录名
         * @return [Builder]
         */
        @Deprecated("使用 lottieAssetAnim")
        fun folderName(folder: String?): Builder = this.also { mAssetsFolderName = folder }

        /**
         * 从动画地址中截取动画所在目录的名称
         *
         * @param url 动画地址
         * @return [Builder]
         */
        fun urlParseFolderName(url: String?): Builder =
            this.also {
                val split: Array<String> = TextUtils.split(url, File.separator)
                val fileName = split[split.size - 1]
                mAssetsFolderName = fileName.substring(0, fileName.indexOf(DOT))
                Logger.e("fileName => $fileName")
            }

        /**
         * 从动画地址中截取动画所在目录的名称
         *
         * @param url 动画地址
         * @return [Builder]
         */
        fun urlParseFolderName(url: String?, secondFolderName: String): Builder =
            this.also {
                val split: Array<String> = TextUtils.split(url, File.separator)
                val fileName = split[split.size - 1]
                mAssetsFolderName = secondFolderName + File.separator + fileName.substring(
                    0,
                    fileName.indexOf(DOT)
                )
                Logger.e("fileName => $fileName")
            }

        /**
         * 设置图像资源
         *
         *
         * 图像存储位置  lottie/动画目录/images 必须放在 这个目录下
         * {如果 json 动画需要设置图像资源，则必须先执行 [Builder.folderName]} 方法
         * 需要使用上下文对象[Builder.with]
         *
         * @return [Builder]
         */
        @Deprecated("使用 lottieAssetAnim")
        fun imageAssetsFolder(): Builder =
            this.also {
                throwable()
                val imageAssets = mContext!!.getString(
                    R.string.format_lottie_name_images_assets_folder,
                    mAssetsFolderName
                )
                mLottie?.imageAssetsFolder = imageAssets
            }

        /**
         * 设置 json 动画资源
         *
         *
         * {如果 json 资源中有引用其他图像资源，则需要先 [Builder.imageAssetsFolder] 方法
         * 需要使用上下文对象[Builder.with]
         *
         * @return [Builder]
         *
         */
        @Deprecated("使用 lottieAssetAnim")
        fun jsonAssetsName(): Builder =
            this.also {
                val assetsAnim =
                    mContext?.getString(R.string.format_lottie_name_anim_json, mAssetsFolderName)
                mLottie?.setAnimation(assetsAnim)
            }

        /**
         * 设置内部资源动画
         * @param folderName assets/lottie/folderName/
         * @param haveImage 是否包含 assets/lottie/folderName/images 图像资源
         */
        fun lottieAssetAnim(folderName: String?, haveImage: Boolean): Builder =
            this.also {
                mLottie?.apply {
                    imageAssetsFolder.takeIf { haveImage }.let {
                        mContext?.getString(
                            R.string.format_lottie_name_images_assets_folder,
                            folderName
                        )
                    }
                    setAnimation(
                        mContext?.getString(
                            R.string.format_lottie_name_anim_json,
                            folderName
                        )
                    )
                }
            }

        /**
         * 从网络加载 lottie 动画
         */
        fun lottieFromUrl(url: String?): Builder =
            this.also {
                val keyName = url?.substring(url.lastIndexOf(LEFT_BIAS) + 1, url.lastIndexOf(DOT))
                Logger.d("keyName -> $keyName")
                mLottie?.setAnimationFromUrl(url, keyName)
            }

        fun lottieFromJson(jsonStr: String): Builder =
            this.also {
                mLottie?.setAnimationFromJson(jsonStr, null)
            }

        /**
         * 设置媒体资源
         *
         * @param rawRes raw 资源 需将资源放置在 res/raw 目录下
         * @return [Builder]
         */
        fun resAssetsName(@RawRes rawRes: Int): Builder =
            this.also { mLottie?.setAnimation(rawRes) }

        /**
         * 设置指定动画
         *
         * @param animation 动画
         * @return [Builder]
         */
        fun animation(animation: Animation?): Builder =
            this.also { mLottie?.animation = animation }

        /**
         * 设置图片资源文件
         *
         * @param drawRes 资源 id
         * @return [Builder]
         */
        fun imageResource(@DrawableRes drawRes: Int): Builder =
            this.also { mLottie?.setImageResource(drawRes) }


        /**
         * 设置图片资源文件
         *
         * @param drawRes 资源 id
         * @return [Builder]
         */
        fun imageTint(color: Int): Builder =
            this.also { mLottie?.imageTintList = ColorStateList.valueOf(color) }

        /**
         * 设置 drawable 图片资源
         *
         * @param drawable drawable资源
         * @return [Builder]
         */
        fun drawable(drawable: Drawable?): Builder =
            this.also { mLottie?.setImageDrawable(drawable) }

        /**
         * 设置重复播放次数
         *
         * @param count {@param count int 类型数值}
         * [该参数为无限制重复][com.airbnb.lottie.LottieDrawable.INFINITE]
         * @return [Builder]
         */
        fun repeatCount(count: Int): Builder = this.also { mLottie?.repeatCount = count }

        /**
         * 设置动画监听器
         * [Builder.lottie]
         * 必须在设置 LottieAnimationView 对象之后才可以使用该方法
         *
         * @return [Builder]
         */
        fun listener(listener: Animator.AnimatorListener): Builder =
            this.also {
                mLottie?.addAnimatorListener(listener)
            }

        /**
         * 执行动画
         * [Builder.lottie]
         * 必须在设置 LottieAnimationView 对象之后才可以使用该方法
         */
        fun play() {
            if (mLottie == null) {
                throw NullPointerException("请调用 lottie(LottieAnimationView) 以配置您的 LottieAnimationView")
            }
            mLottie?.playAnimation()
        }

        /**
         * 添加 lottie 动画成分监听
         * 用于遍历动画成分
         */
        fun addLottieOnCompositionLoadedListener() {
            mLottie?.addLottieOnCompositionLoadedListener {
                mLottie?.resolveKeyPath(KeyPath("**"))?.forEach {
                    Logger.d("lottie key path => ${it.keysToString()}")
                }
                setUpValueCallback()
            }
        }

        /**
         * 设置 valueCallback 成分监听回执
         * 用于对 lottie 动画文件的 字段修改
         */
        fun setUpValueCallback() {
            //todo example
            mLottie?.apply {
                addValueCallback(KeyPath("1", "2", "3"), LottieProperty.COLOR) {
                    ContextCompat.getColor(mContext!!, R.color.color_white_FFF)
                }
            }
        }

        /**
         * 清除原有动画
         * [Builder.lottie]
         * 必须在设置 LottieAnimationView 对象之后才可以使用该方法
         *
         * @return [Builder]
         */
        fun clear(): Builder = this.also { mLottie?.clearAnimation() }

        private fun throwable() {
            if (mLottie == null) {
                throw NullPointerException("请调用 lottie(LottieAnimationView) 以配置您的 LottieAnimationView")
            } else if (mAssetsFolderName.isNullOrEmpty()) {
                val throwable = Throwable()
                throwable.stackTrace = arrayOf(
                    StackTraceElement(
                        "com.hssenglish.baselibrary.utils.LottieBuilder",
                        "imageAssetsFolder",
                        "LottieBuilder",
                        throwable.stackTrace[0].lineNumber
                    )
                )
                throw IllegalStateException(
                    "请先调用 folderName(String) 方法设置图像资源的上一级目录，且该方法需要在 imageAssetsFolder() 及 jsonAssetsName(String) 方法之前调用!",
                    throwable
                )
            }
        }
    }
}