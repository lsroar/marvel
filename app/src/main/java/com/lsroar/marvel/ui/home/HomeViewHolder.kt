package com.lsroar.marvel.ui.home

import android.content.Context
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.lsroar.marvel.R
import com.lsroar.marvel.ui.home.entity.CharacterVO

class HomeViewHolder(
    itemView: View,
    private val context: Context
) : RecyclerView.ViewHolder(itemView) {

    private val name = itemView.findViewById<TextView>(R.id.textview_item_view_home_name)
    private val image = itemView.findViewById<ImageView>(R.id.imageview_item_view_character)
    var id: Int = 0
        private set

    fun bind(itemData: CharacterVO) {
        name.text = itemData.name
        id = itemData.id

        Glide.with(this.context)
            .load(itemData.imageUrl + "/landscape_amazing.jpg")
            .placeholder(R.mipmap.ic_launcher)
            .apply(RequestOptions().centerInside())
            .into(image)
    }
}
