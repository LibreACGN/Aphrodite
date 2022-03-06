package com.sodalaboratory.aphrodite.utils

import android.widget.Toast
import com.sodalaboratory.aphrodite.AphroditeApplication

// Toast 工具函数
fun String.showToast() {
    Toast.makeText(AphroditeApplication.context, this, Toast.LENGTH_SHORT).show()
}

fun Int.showToast() {
    Toast.makeText(AphroditeApplication.context, this, Toast.LENGTH_SHORT).show()
}