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
import androidx.core.view.isVisible
import com.example.gametracker.features.bord.presentation.adapter.GiveawayAdapter


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
        viewBinding.tbFilter.setOnCheckedChangeListener { buttonView, isChecked ->
            val params = viewBinding.llSort.layoutParams as ConstraintLayout.LayoutParams
            if (isChecked) {
                params.topMargin = 0
                params.startToStart = ConstraintSet.PARENT_ID
                params.startToEnd = ConstraintSet.UNSET
                params.topToBottom = viewBinding.tvSelectSort.id
                params.topToTop = ConstraintSet.UNSET
                viewBinding.tvSelectSortGone.visibility = View.GONE
                viewBinding.filterGroup.visibility = View.VISIBLE

            } else {
                viewBinding.tvSelectSortGone.visibility = View.VISIBLE

                viewBinding.filterGroup.visibility = View.GONE
                params.startToStart = ConstraintSet.UNSET
                params.startToEnd = viewBinding.tvSelectSortGone.id
                params.topMargin = resources.getDimension(R.dimen.start_margin_giveaway).toInt()
                params.topToBottom = ConstraintSet.UNSET
                params.topToTop = ConstraintSet.PARENT_ID
            }
            viewBinding.llSort.requestLayout()

        }
        viewModel.exception.observe(viewLifecycleOwner, {
            Log.e("Exception", it.message?:it.toString())
            viewBinding.tvNotFound.visibility = View.VISIBLE
            viewBinding.rvGiveaway.visibility = View.GONE
        })
        viewModel.loadingState.observe(viewLifecycleOwner, {
            viewBinding.progress.root.isVisible = it
        })
        viewBinding.tbFilter.isChecked = false
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
        viewModel.giveaways.observe(viewLifecycleOwner, {
            val adapter = GiveawayAdapter(it)
            viewBinding.rvGiveaway.adapter = adapter
        })
        viewBinding.btnFind.setOnClickListener {
            viewBinding.rvGiveaway.visibility = View.VISIBLE
            viewBinding.tvNotFound.visibility = View.GONE
            var platforms: String? = ""
            for (filter in platformsFilter) {
                platforms += filter.platformName
            }
            platforms = if (platformsFilter.isEmpty())
                null
            else
                platforms!!.subSequence(1, platforms.length).toString()
            if (typesFilter?.isEmpty() == true){
                    typesFilter =  null
                }
            typesFilter = typesFilter?.replace("null", "")

            viewModel.getFilterGiveaway(platforms, typesFilter, sortFilter)
            viewBinding.tbFilter.isChecked = false
        }
        viewBinding.btnCancel.setOnClickListener {
            platformsFilter = mutableSetOf<Platform>()
            val types = mutableSetOf<MaterialCardView>()
            types.add(viewBinding.cvLoot)
            types.add(viewBinding.cvGame)
            types.add(viewBinding.cvBeta)
            for (card in types){
                card.isChecked = false
                card.backgroundTintList =
                    resources.getColorStateList(R.color.surface)
            }
            typesFilter = null
            sortFilter = "date"
            viewModel.getPlatfroms()
            viewBinding.tbFilter.isChecked = false
            viewBinding.cvDate.performClick()

        }
        viewBinding.btnFind.performClick()
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

    }

    private fun cardTypeToggle(card: MaterialCardView, type: String) {
        val isChecked = card.isChecked
        if (isChecked) {
            typesFilter += type
            card.backgroundTintList =
                resources.getColorStateList(R.color.primary)
        } else {
            typesFilter = typesFilter?.replace(type, "")
            card.backgroundTintList =
                resources.getColorStateList(R.color.surface)
        }

    }

}