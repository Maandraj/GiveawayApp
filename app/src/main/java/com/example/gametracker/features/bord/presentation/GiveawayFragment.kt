package com.example.gametracker.features.bord.presentation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.os.Bundle
import android.os.Parcelable
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import by.kirich1409.viewbindingdelegate.viewBinding
import com.example.gametracker.R
import com.example.gametracker.databinding.FragmentGiveawayBinding
import com.example.gametracker.features.bord.domain.model.Platform
import com.example.gametracker.features.bord.presentation.adapter.PlatformAdapter
import com.example.gametracker.features.bord.utils.PlatformSupport
import com.google.android.material.card.MaterialCardView
import dagger.hilt.android.AndroidEntryPoint
import android.widget.ToggleButton
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet


@AndroidEntryPoint
class GiveawayFragment : Fragment(R.layout.fragment_giveaway), PlatformAdapter.ClickListener {
    private val viewModel: GiveawayViewModel by viewModels()
    private val viewBinding: FragmentGiveawayBinding by viewBinding(FragmentGiveawayBinding::bind)
    private var platformsFilter: MutableSet<Platform> = mutableSetOf()
    private var typesFilter: String? = ""
    private var sortFilter: String = ".date"
    private lateinit var adapter: PlatformAdapter
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewBinding.cvGame.setOnClickListener {
            viewBinding.cvGame.toggle()
            cardTypeToggle(it as MaterialCardView, ".game")
        }
        viewBinding.cvLoot.setOnClickListener {
            viewBinding.cvLoot.toggle()
            cardTypeToggle(it as MaterialCardView, ".loot")
        }
        viewBinding.cvBeta.setOnClickListener {
            viewBinding.cvBeta.toggle()
            cardTypeToggle(it as MaterialCardView, ".beta")
        }

        viewBinding.cvDate.setOnClickListener {
            val card = it as MaterialCardView
            cardSorting(card, card.contentDescription.toString())
        }
        viewBinding.cvValue.setOnClickListener {
            val card = it as MaterialCardView
            cardSorting(card, card.contentDescription.toString())
        }
        viewBinding.cvPopularity.setOnClickListener {
            val card = it as MaterialCardView
            cardSorting(card, card.contentDescription.toString())
        }
        viewBinding.cvDate.performClick()

        viewModel.platforms.observe(viewLifecycleOwner, {
            adapter = PlatformAdapter(it, this, platformsFilter ?: mutableSetOf())
            viewBinding.rvPlatform.adapter = adapter

        })
        viewBinding.tbFilter.setOnCheckedChangeListener { buttonView, isChecked ->
            val params = viewBinding.llSort.layoutParams as ConstraintLayout.LayoutParams
            if (isChecked) {
                params.topMargin = 0
                params.topToBottom = viewBinding.tvSelectSort.id
                params.topToTop = ConstraintSet.UNSET
                viewBinding.filterGroup.visibility = View.VISIBLE

            } else {
                viewBinding.filterGroup.visibility = View.GONE

                params.topMargin = resources.getDimension(R.dimen.start_margin_giveaway).toInt()
                params.topToBottom = ConstraintSet.UNSET
                params.topToTop = ConstraintSet.PARENT_ID

            }
            viewBinding.llSort.requestLayout()

        }
        viewBinding.tbFilter.isChecked = false
        viewBinding.btnFind.setOnClickListener {
            var platforms: String? = ""
            for (filter in platformsFilter) {
                platforms += filter.platformName
            }


            platforms = if (platformsFilter.isEmpty())
                null
            else
                platforms!!.subSequence(1, platforms.length).toString()
            typesFilter = if (typesFilter?.isEmpty() == true)
                null
            else
                typesFilter?.subSequence(1, typesFilter!!.length).toString()
            sortFilter = sortFilter.subSequence(1, sortFilter.length).toString()

            viewModel.getFilterGiveaway(platforms, typesFilter, sortFilter)
            viewBinding.tbFilter.isChecked = false
        }
        viewBinding.btnCancel.setOnClickListener {
            viewBinding.tbFilter.isChecked = false
        }
    }

    override fun onClickItem(platforms: MutableSet<Platform>) {
        platformsFilter = platforms
    }

    private fun cardSorting(card: MaterialCardView, sort: String) {
        val noActive = resources.getColorStateList(R.color.surface)
        viewBinding.cvDate.backgroundTintList = noActive
        viewBinding.cvValue.backgroundTintList = noActive
        viewBinding.cvPopularity.backgroundTintList = noActive
        sortFilter = sort
        card.backgroundTintList =
            resources.getColorStateList(R.color.primary)
        Log.i("sort", sort)
        if (!viewBinding.tbFilter.isChecked) {
            viewBinding.btnFind.performClick()
        }


//        if (!viewBinding.cvDate.isChecked && !viewBinding.cvValue.isChecked && !viewBinding.cvPopularity.isChecked )
//            viewBinding.cvDate.isChecked = true
    }

    private fun cardTypeToggle(card: MaterialCardView, type: String) {


        val isChecked = card.isChecked
        if (isChecked) {
            typesFilter += type
            card.backgroundTintList =
                resources.getColorStateList(R.color.primary)
        } else {
            typesFilter?.replace(type, "")
            card.backgroundTintList =
                resources.getColorStateList(R.color.surface)
        }

    }

}