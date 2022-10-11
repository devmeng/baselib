package com.devmeng.baselib.utils

import android.util.Log
import com.devmeng.baselib.net.ApiConstant

/**
 * Created by Richard -> MHS
 * Date : 2022/5/30  16:14
 * Version : 1
 */
class Logger {

    companion object {
        var TAG: String = "Logger"
        fun d(msg: String) {
            if (ApiConstant.BUILD_TYPE) {
                Log.d(TAG, "msg -> $msg")
            }
        }

        fun e(errorMsg: String) {
            if (ApiConstant.BUILD_TYPE) {
                Log.e(TAG, "errorMsg -> $errorMsg")
            }
        }

        fun i(info: String) {
            if (ApiConstant.BUILD_TYPE) {
                Log.i(TAG, "<[$info]>")
            }
        }

        fun w(warnMsg: String) {
            if (ApiConstant.BUILD_TYPE) {
                Log.w(TAG, "<!![$warnMsg]!!>")
            }
        }
    }

}