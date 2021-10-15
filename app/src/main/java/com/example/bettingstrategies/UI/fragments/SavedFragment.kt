package com.example.bettingstrategies.UI.fragments

import androidx.fragment.app.Fragment
import com.example.bettingstrategies.R
import com.example.bettingstrategies.models.Strategy
import com.example.bettingstrategies.utilits.getStrategyData
import com.example.bettingstrategies.utilits.replaceFragment
import kotlinx.android.synthetic.main.fragment_saved.*


class SavedFragment : Fragment(R.layout.fragment_saved) {

    override fun onResume() {
        super.onResume()
        getStrategyData(false, rec_view_2, requireContext())
    }

}