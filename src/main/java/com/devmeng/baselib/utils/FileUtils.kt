package com.devmeng.baselib.utils

import android.os.Environment
import com.devmeng.baselib.net.DataService
import okhttp3.Call
import okhttp3.Callback
import okhttp3.Request
import okhttp3.Response
import java.io.File
import java.io.FileOutputStream
import java.io.IOException

/**
 * Created by Richard -> MHS
 * Date : 2022/10/30  21:42
 * Version : 1
 */
class FileUtils {

    /**
     * 下载网络文件
     * @param fileUrl 网络文件路径
     * @param dirName 文件存储目录
     * @param fileName 文件名
     *
     */
    fun downloadFile(
        fileUrl: String,
        dirName: String,
        fileName: String,
        fileCallback: (File, String?) -> Unit
    ) {
        val dir = File("${Environment.getExternalStorageDirectory()}${File.separator}$dirName")
        if (!dir.exists()) {
            dir.mkdir()
        }
        val file = File(dir, fileName)
        if (file.exists()) {
            Logger.d("[${file.name}]文件存在")
//            return file
        }
        val tempFile = File(file.parentFile, "${file.name}.temp")
        val request = Request.Builder().url(fileUrl).build()
        DataService.getOkHttpClient().newCall(request).enqueue(object : Callback {
            override fun onFailure(call: Call, e: IOException) {
                e.printStackTrace()
            }

            override fun onResponse(call: Call, response: Response) {
//                Logger.d("[download skin file response body string]")
//                Logger.d("[$json]")
//                Logger.d("=========================================")
                val fileOut = FileOutputStream(tempFile)
                try {
                    val bs = response.body.source().inputStream()
                    var length: Int
                    val bytes = ByteArray(10240)
                    while (bs.read(bytes).also { length = it } != -1) {
                        fileOut.write(bytes, 0, length)
                    }
                    tempFile.renameTo(file)
                    Logger.d("md5 by skin path -> [${md5ForFile(file)}]")

                    fileCallback(file, dir.path)

                } catch (e: Exception) {
                    Logger.e("${e.message}")
                    Logger.e(e.stackTraceToString())
                } finally {
                    fileOut.close()
                }
            }
        })
    }

}