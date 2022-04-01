package com.lsroar.marvel.ui.home

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.lsroar.marvel.R
import com.lsroar.marvel.ui.home.entity.CharacterVO

class HomeAdapter(
    private val homeList: List<CharacterVO>,
    private val homeViewModel: HomeViewModel,
    private val context: Context
) : RecyclerView.Adapter<HomeViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HomeViewHolder {
        val view =
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_view_home, parent, false)

        val holder = HomeViewHolder(view, context)
        holder.itemView.setOnClickListener {
            homeViewModel.onItemClick(holder.id)
        }

        return holder
    }

    override fun onBindViewHolder(holder: HomeViewHolder, position: Int) {
        holder.bind(homeList[position])
    }

    override fun getItemCount(): Int {
        return homeList.size
    }
}
