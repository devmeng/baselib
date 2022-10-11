package com.devmeng.baselib.net

import com.devmeng.baselib.utils.ConstantUtils

/**
 * Created by Richard -> MHS
 * Date : 2022/5/30  15:56
 * Version : 1
 */
interface ApiConstant {

    companion object {

        var BUILD_TYPE: Boolean = com.devmeng.baselib.BuildConfig.DEBUG

        var BASE_URL: String = when {
            BUILD_TYPE -> "https://www.wanandroid.com"
            else -> "https://www.wanandroid.com"
        }

    }

    interface HomePage {
        companion object {
            //get 文章列表
            const val ARTICLE_LIST = "article/list/{${ConstantUtils.PATH.PAGE}}/json"

            //首页 Banner
            const val HOME_BANNER = "banner/json"

            //常用网络
            const val USUALLY_USE_NETWORK = "friend/json"

            //置顶文章
            const val STICK_TOP_ARTICLE = "article/top/json"
        }
    }

    interface UserOperation {
        companion object {
            /**
             * 参数
             * 文章 id:拼接在 url 上
             * title: 文章标题
             * link: 文章 url
             * author: 作者
             */
            const val COLLECT_ARTICLE =
                "lg/collect/user_article/update/{${ConstantUtils.PATH.ARTICLE_ID}}/json"

            const val CANCEL_COLLECT_ARTICLE =
                "lg/uncollect_originId/{${ConstantUtils.PATH.ARTICLE_ID}}/json"

            const val SIGN_IN = ""

        }
    }

}