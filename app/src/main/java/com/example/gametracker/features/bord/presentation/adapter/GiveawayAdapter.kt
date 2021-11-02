package com.example.gametracker.features.bord.presentation.adapter

import android.annotation.SuppressLint
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.bumptech.glide.Glide
import com.example.gametracker.R
import com.example.gametracker.databinding.GiveawayItemBinding
import com.example.gametracker.features.bord.domain.model.Giveaway

class GiveawayAdapter(
    private val giveaways: List<Giveaway>,
) : RecyclerView.Adapter<GiveawayAdapter.GiveawayHolder>() {
    class GiveawayHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: GiveawayItemBinding by viewBinding(GiveawayItemBinding::bind)
        val image = binding.ivImage
        val stars = binding.usersItem
        val title = binding.tvTitle
        val price = binding.tvPrice
        val type = binding.typeItem
        val rare = binding.rareItem
        val description = binding.tvDescription

        //icons
        val windows = binding.ivWindows
        val ios = binding.ivIos
        val android = binding.ivPlayMarket
        val swift = binding.ivSwift
        val ps = binding.ivPlaystation
        val xbox = binding.ivXbox

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GiveawayHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.giveaway_item, parent, false)
        return GiveawayHolder(itemView)
    }

    @SuppressLint("UseCompatLoadingForColorStateLists")
    override fun onBindViewHolder(holder: GiveawayHolder, position: Int) {
        val giveaway = giveaways[position]
        val platforms = giveaway.platforms
        val star = giveaway.users
        holder.type.tvTitle.text = giveaway.type
        if (star > 0) {
            holder.rare.cvType.backgroundTintList =
                holder.itemView.resources.getColorStateList(R.color.rarity_rare)
            holder.rare.tvTitle.text = holder.itemView.context.getString(R.string.rare)
        }
        if (star > 2000) {
            holder.rare.cvType.backgroundTintList =
                holder.itemView.resources.getColorStateList(R.color.rarity_mystical)
            holder.rare.tvTitle.text = holder.itemView.context.getString(R.string.mystical)
        }
        if (star > 4500) {
            holder.rare.cvType.backgroundTintList =
                holder.itemView.resources.getColorStateList(R.color.rarity_immortal)
            holder.rare.tvTitle.text = holder.itemView.context.getString(R.string.immortal)
        }
        holder.rare.cvType.requestLayout()

        if (platforms.indexOf("PC") != -1)
            holder.windows.visibility = View.VISIBLE


        if (platforms.indexOf("Swift") != -1)
            holder.swift.visibility = View.VISIBLE


        if (platforms.indexOf("Ios") != -1)
            holder.ios.visibility = View.VISIBLE


        if (platforms.indexOf("Android") != -1)
            holder.android.visibility = View.VISIBLE


        if (platforms.indexOf("Playstation") != -1)
            holder.ps.visibility = View.VISIBLE


        if (platforms.indexOf("Xbox") != -1)
            holder.xbox.visibility = View.VISIBLE



        Glide.with(holder.itemView).load(giveaway.image).into(holder.image)
        holder.price.apply {
            paintFlags = paintFlags or Paint.STRIKE_THRU_TEXT_FLAG
            text = giveaway.worth.replace("N/A", "$0")
        }
        holder.stars.tvTitle.text = giveaway.users.toString()
        holder.description.text = giveaway.description
        holder.title.text = giveaway.title
    }

    override fun getItemCount() = giveaways.size
}