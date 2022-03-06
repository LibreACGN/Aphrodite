package com.sodalaboratory.aphrodite.data.model

import android.content.Context
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sodalaboratory.aphrodite.AphroditeApplication
import com.sodalaboratory.aphrodite.R

/*
    实体 Person (区别于安卓已有的 Person)
    uid，
    name 姓名
    birthday 生日
    // 图片路径 iconPath，使用 uid


    内部 id，自动生成，作为主键
 */
@Entity
data class Person(
    @ColumnInfo(name = "name") var name: String =
        AphroditeApplication.context.getString(R.string.person_name_hint),
    @ColumnInfo(name = "birthday") var birthday: String = "Feb. 12",
    @ColumnInfo(name = "image_path") var imagePath: String = "") {

    @PrimaryKey(autoGenerate = true) var innerId: Long = 0
}