package com.sodalaboratory.aphrodite.ui

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.sodalaboratory.aphrodite.AphroditeApplication
import com.sodalaboratory.aphrodite.R
import com.sodalaboratory.aphrodite.data.model.Person


// RecyclerView Adapter
class PersonAdapter(val personList: MutableList<Person>) :
    RecyclerView.Adapter<PersonAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val personImage = view.findViewById<ImageView>(R.id.person_icon_image_view)
        val personName = view.findViewById<TextView>(R.id.person_name_text_view)
        val personBirthday = view.findViewById<TextView>(R.id.person_birthday_text_view)
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
        Glide.with(AphroditeApplication.context).load(R.drawable.outline_account_circle_24).into(holder.personImage)
//        holder.personImage.setImageResource(R.drawable.outline_account_circle_24)
//         加载图片
//        if (person.imagePath != "") // 不为 ""，加载已经设置好的图片
//            Glide.with(AphroditeApplication.context).load(person.imagePath).into(holder.personImage)
//        else // 为""，加载资源
//            Glide.with(AphroditeApplication.context).load(R.drawable.outline_account_circle_24).into(holder.personImage)
    }

    override fun getItemCount() = personList.size

}