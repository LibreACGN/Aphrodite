package com.sodalaboratory.aphrodite.ui

import android.app.Activity
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.sodalaboratory.aphrodite.AphroditeApplication
import com.sodalaboratory.aphrodite.R
import com.sodalaboratory.aphrodite.utils.FileUtil
import com.sodalaboratory.aphrodite.utils.LogUtil
import com.sodalaboratory.aphrodite.utils.showToast

class EditPersonActivity : AppCompatActivity() {
    // 照片 名字 生日
    private var isImageUriSet = false
    private var isNameSet = false
    private var isBirthdaySet = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_person)

        val editNameEditText = findViewById<EditText>(R.id.person_name_edittext)
        val editBirthdayEditText = findViewById<EditText>(R.id.person_birthday_edittext)
        val selectImageButton = findViewById<Button>(R.id.select_image_button)
        val okButton = findViewById<Button>(R.id.ok_button)
        val cancelButton = findViewById<Button>(R.id.cancel_button)

        selectImageButton.setOnClickListener {
            // 选择图片
            // 图片大小控制在 4mb 减轻压缩负担
            ImagePicker.with(this)
                .crop()
                .compress(4096)
                .start()
        }

        okButton.setOnClickListener {
            val name = editNameEditText.text.toString()
            val birthday = editBirthdayEditText.text.toString()
            if (name != "") isNameSet = true
            if (birthday != "") isBirthdaySet = true
            if (isNameSet && isBirthdaySet && isImageUriSet) {
                // 以人名为文件名存储数据
                if(FileUtil.fileExists(name)) {
                    "存在重名，请重新设定！".showToast()
                } else {
                    // 不重名 存储
                    FileUtil.save(name)
                    // 返回数据
                    val intent = Intent().apply {
                        putExtra("name", name)
                        putExtra("birthday", birthday)
                        putExtra("image_path", "${FileUtil.appImageFolder}${name}.jpg")
                    }
                    setResult(Activity.RESULT_OK, intent)
                    finish()
                }
            }

        }

        cancelButton.setOnClickListener {
            finish()
        }
    }


    override fun onStart() {
        super.onStart()
        when (AphroditeApplication.context.resources?.configuration?.uiMode?.and(
            Configuration.UI_MODE_NIGHT_MASK
        )) {
            Configuration.UI_MODE_NIGHT_YES -> {
                supportActionBar?.setBackgroundDrawable(ColorDrawable(Color.parseColor("#FFFFFFFF")))
            }
        }
    }

    // 处理图片回调
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

            when (requestCode) {
                // 图片选取
                2404 -> {
                    // 待改进 用 launcher 启动活动
                    // RESULT_OK 则 Uri 不为空
                    if (resultCode == RESULT_OK) {
                        val imageUri = data?.data!!
                        LogUtil.d("exist", requestCode.toString())
                        FileUtil.saveDefaultImage(imageUri.path!!)
                        // 加载暂存的图片
                        Glide.with(this).load(imageUri.path)
                                                .into(findViewById(R.id.person_image_preview))
                        isImageUriSet = true
                    }
                }
            }
    }
}