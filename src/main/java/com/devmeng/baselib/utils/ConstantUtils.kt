package com.devmeng.baselib.utils

/**
 * Created by Richard -> MHS
 * Date : 2022/5/30  18:25
 * Version : 1
 */
class ConstantUtils {

    companion object {

        const val EMPTY: String = ""

        const val SPACE: String = " "

        const val DOT: String = "."

    }

    interface Action {
        companion object {
            const val ANDROID_NET_CHANGE_ACTION = "android.net.conn.CONNECTIVITY_CHANGE"
        }
    }

    interface PATH {
        companion object {
            const val PAGE = "PAGE"
            const val ARTICLE_ID = "article_id"
        }
    }

    interface Extra {
        companion object {
            const val WEB_TITLE = "web_title"
            const val WEB_URL = "web_url"
            const val WIDGET_TYPE = "widget_type"
        }
    }

    interface Params {
        companion object {
            const val ACCOUNT = "account"
            const val PASSWORD = "password"
        }
    }

    interface Chat {
        companion object {
            const val CHAT_EXTRA = "chat_extra"
        }
    }

}