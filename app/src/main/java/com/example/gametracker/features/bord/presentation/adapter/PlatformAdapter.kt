package com.example.gametracker.features.bord.presentation.adapter

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.gametracker.R
import com.example.gametracker.databinding.PlatformItemBinding
import com.example.gametracker.features.bord.domain.model.Platform
import com.example.gametracker.features.bord.utils.PlatformLogo
import com.google.android.material.card.MaterialCardView

class PlatformAdapter(
    private val platforms: List<Platform>,
    private val onClickListener: ClickListener,
    private val platformsSelected : MutableSet<Platform>
) :
    RecyclerView.Adapter<PlatformAdapter.PlatformHolder>() {

    class PlatformHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding: PlatformItemBinding by viewBinding(PlatformItemBinding::bind)
        val logo = binding.ivLogo
        val name = binding.tvName
        val card = binding.cvFilter
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlatformHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.platform_item, parent, false)
        return PlatformHolder(itemView)
    }

    @SuppressLint("ResourceType")
    override fun onBindViewHolder(holder: PlatformHolder, position: Int) {
        val platform = platforms[position]
        holder.card.setOnClickListener {
            (it as MaterialCardView).toggle()
        }
        holder.card.setOnCheckedChangeListener { card, isChecked ->
            if (isChecked) {
                platformsSelected.add(element = platform)
                card.backgroundTintList =
                    holder.itemView.resources.getColorStateList(R.color.primary)
                card.requestLayout()
            } else {
                platformsSelected.remove(element = platform)
                card.backgroundTintList =
                    holder.itemView.resources.getColorStateList(R.color.surface)
                holder.card.requestLayout()
            }
            onClickListener.onClickItem(platformsSelected)
        }
        if (platformsSelected.indexOf(platform) != -1) {
            holder.card.backgroundTintList =
                holder.itemView.resources.getColorStateList(R.color.primary)
            holder.card.requestLayout()
        } else if (platformsSelected.indexOf(platform) == -1) {
            holder.card.backgroundTintList =
                holder.itemView.resources.getColorStateList(R.color.surface)
            holder.card.requestLayout()
        }
        val logoId = PlatformLogo.getLogo(platform.name)
        holder.logo.setImageResource(logoId)
        holder.name.text = platform.name
        Log.i("POS", platformsSelected.toString())
    }



    override fun getItemCount() = platforms.size

    interface ClickListener {
        fun onClickItem(platforms: MutableSet<Platform>)
    }
}