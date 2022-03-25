package com.sodalaboratory.aphrodite.ui.recyclerview

import android.content.Context
import android.content.res.Configuration
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sodalaboratory.aphrodite.AphroditeApplication
import com.sodalaboratory.aphrodite.R
import com.sodalaboratory.aphrodite.data.model.Person
import com.sodalaboratory.aphrodite.utils.LogUtil


// RecyclerView Adapter
class PersonAdapter(val context: Context, val personList: MutableList<Person>) :
    RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val personImage: ImageView = view.findViewById(R.id.person_icon_image_view)
        val personName: TextView = view.findViewById(R.id.person_name_text_view)
        val personBirthday: TextView = view.findViewById(R.id.person_birthday_text_view)
        val layout: LinearLayout = view.findViewById(R.id.person_info_linearlayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.person_card_view, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val person = personList[position]
        holder.personName.text = person.name
        holder.personBirthday.text = person.birthday

        // 根据模式设置颜色
        when (AphroditeApplication.context.resources?.configuration?.uiMode?.and(
            Configuration.UI_MODE_NIGHT_MASK
        )) {
            Configuration.UI_MODE_NIGHT_YES -> {
                LogUtil.d("PersonAdapter", "UI_MODE_NIGHT_YES")
                // 设置背景色
                holder.layout.setBackgroundColor(ContextCompat.
                getColor(holder.itemView.context, R.color.black))
            }
            else -> {
                holder.layout.setBackgroundColor(ContextCompat.
                getColor(holder.itemView.context, R.color.light_gray))
            }
        }

        if (person.imagePath == "")
            // 无图片，加载默认
            holder.personImage.setImageResource(R.drawable.outline_account_circle_24)
        else // 有图片 加载图片
            Glide.with(context).load(person.imagePath).into(holder.personImage)
    }

    override fun getItemCount() = personList.size

}