package com.sodalaboratory.aphrodite.ui.recyclerview

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sodalaboratory.aphrodite.R
import com.sodalaboratory.aphrodite.data.model.Person


// RecyclerView Adapter
class PersonAdapter(val context: Context, val personList: MutableList<Person>) :
    RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val personImage: ImageView = view.findViewById<ImageView>(R.id.person_icon_image_view)
        val personName: TextView = view.findViewById<TextView>(R.id.person_name_text_view)
        val personBirthday: TextView = view.findViewById<TextView>(R.id.person_birthday_text_view)
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
        if (person.imagePath == "")
            // 无图片，加载默认
            holder.personImage.setImageResource(R.drawable.outline_account_circle_24)
        else // 有图片 加载图片
            Glide.with(context).load(person.imagePath).into(holder.personImage)
    }

    override fun getItemCount() = personList.size

}