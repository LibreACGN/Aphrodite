package com.sodalaboratory.aphrodite

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context

// 获取全局 Context
class AphroditeApplication : Application() {

    companion object {
        @SuppressLint("StaticFieldLeak")
        lateinit var context: Context
    }

    override fun onCreate() {
        super.onCreate()
        context = applicationContext
    }
}