package com.example.bettingstrategies.UI.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bettingstrategies.R
import com.example.bettingstrategies.UI.fragments.FirstLaunchFragment
import com.example.bettingstrategies.UI.fragments.MoreFragment
import com.example.bettingstrategies.UI.fragments.SavedFragment
import com.example.bettingstrategies.models.SavedStrategy
import com.example.bettingstrategies.models.Strategy
import com.example.bettingstrategies.utilits.*
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.card_view.view.*

class RecAdapter(val status: Boolean, val context: Context, val  recyclerView: RecyclerView): RecyclerView.Adapter<RecAdapter.MyViewHolder>() {

    private var strategyList = emptyList<Strategy>()

    class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.card_view, parent, false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = strategyList[position]
        val image = currentItem.imageUrl
        val name = currentItem.name

        holder.itemView.more.setOnClickListener(object : View.OnClickListener{
            override fun onClick(v: View?) {
                val activity = v!!.context as AppCompatActivity
                activity.supportFragmentManager.beginTransaction()
                    .addToBackStack(null)
                    .replace(
                        R.id.dataContainer,
                        MoreFragment(status, currentItem)
                    ).commit()
            }

        })

        if(status){
            holder.itemView.button.text = "Save to favorites"
            holder.itemView.button.setOnClickListener(object : View.OnClickListener{
                override fun onClick(v: View?) {
                    FirebaseDatabase.getInstance().getReference(NODE_USERS).child(USER.uid.toString()).child(NODE_SAVED).child(name.toString())
//                    .child("$year").child("$month").child("$day")
                        .child("name").setValue(name.toString())
                        .addOnSuccessListener {
                            showToast("Saved!")
                        }
                        .addOnFailureListener {
                            showToast("Don`t saved!")
                        }
                }
            })
        }else{
            holder.itemView.button.visibility = View.INVISIBLE
//            holder.itemView.button.setOnClickListener(object : View.OnClickListener{
//                override fun onClick(v: View?) {
//                    FirebaseDatabase.getInstance().getReference(NODE_USERS).child(USER.uid.toString()).child(NODE_SAVED)
////                    .child("$year").child("$month").child("$day")
//                        .child(name.toString()).removeValue()
//                        .addOnSuccessListener {
//                            getStrategyData(false, recyclerView, context)
//                            showToast("Deleted!")
//                        }
//                        .addOnFailureListener {
//                            showToast("Don`t deleted!")
//                        }
//                }
//            })
        }

        holder.itemView.text.text = name
        Glide.with(APP_ACTIVITY)
            .load(image)
            .into(holder.itemView.image)

    }

    override fun getItemCount(): Int {
        return strategyList.size
    }

    fun setData(check: List<Strategy>){
        this.strategyList = check
        notifyDataSetChanged()
    }
}