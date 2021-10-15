package com.example.bettingstrategies.UI.fragments


import androidx.fragment.app.Fragment
import com.example.bettingstrategies.R
import com.example.bettingstrategies.models.Strategy
import com.example.bettingstrategies.utilits.getStrategyData
import com.example.bettingstrategies.utilits.replaceFragment
import kotlinx.android.synthetic.main.fragment_first_launch.*


class FirstLaunchFragment : Fragment(R.layout.fragment_first_launch) {

    override fun onResume() {
        super.onResume()
        getStrategyData(true, rec_view_1, requireContext())
    }
}


