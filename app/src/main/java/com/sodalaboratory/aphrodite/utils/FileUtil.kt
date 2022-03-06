package com.sodalaboratory.aphrodite.utils


import android.net.Uri
import com.sodalaboratory.aphrodite.AphroditeApplication
import java.io.File

object FileUtil {
    // 图片文件夹路径
    var appImageFolder: String = AphroditeApplication.context.filesDir.absolutePath + "/person_icons/"

    // 判断图片文件夹是否存在
    fun init() {
        if (!File(appImageFolder).exists()) File(appImageFolder).mkdir()
        LogUtil.d("DEBUG", "picture folder created")
    }


    // 从暂存的文件复制到新的以人名命名的文件中
    fun save(name: String) {
        File("$appImageFolder.jpg").copyTo(File(appImageFolder + "${name}.jpg"))
        File("$appImageFolder.jpg").delete()
    }


    fun saveDefaultImage(sourceFilePath: String) {
        // 先删除 防止重复
        File("$appImageFolder.jpg").delete()
        if(!File("$appImageFolder.jpg").exists()) LogUtil.d("exist", ".jpg")
        File(sourceFilePath).copyTo(File("$appImageFolder.jpg"))
        if(!File("$appImageFolder.jpg").exists()) LogUtil.d("exist", ".jpg")
    }

    fun fileExists(name: String) = File(appImageFolder + "${name}.jpg").exists()

    fun getDefaultImagePath() = "$appImageFolder.jpg"

    fun delete(name: String): Boolean = File(appImageFolder + "${name}.jpg").delete()
}