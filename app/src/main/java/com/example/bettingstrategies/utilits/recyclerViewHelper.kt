package com.example.bettingstrategies.utilits

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.bettingstrategies.UI.adapters.RecAdapter
import com.example.bettingstrategies.models.Strategy

fun initRecyclerView(status: Boolean, recyclerView: RecyclerView, requireContext: Context, strategyList: List<Strategy>){
    val adapter = RecAdapter(status, requireContext, recyclerView)
    Log.d("kek1",strategyList.toString())
    adapter.setData(strategyList)
    recyclerView.adapter = adapter
    recyclerView.layoutManager = LinearLayoutManager(requireContext, RecyclerView.HORIZONTAL, false )
}