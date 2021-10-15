package com.example.bettingstrategies.UI.fragments

import android.view.View
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.example.bettingstrategies.R
import com.example.bettingstrategies.models.Strategy
import com.example.bettingstrategies.utilits.*
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_more.*


class MoreFragment(val boolean: Boolean, private val strategy: Strategy) : Fragment(R.layout.fragment_more) {

    lateinit var REF_DATABASE_ROOT: DatabaseReference

    override fun onResume() {
        super.onResume()

        REF_DATABASE_ROOT = FirebaseDatabase.getInstance().getReference(NODE_USERS).child(USER.uid.toString()).child(
            NODE_SAVED
        ).child(strategy.name.toString())

        textmore.text = strategy.text.toString()
        text.text = strategy.name.toString()
        Glide.with(APP_ACTIVITY)
            .load(strategy.imageUrl)
            .into(image)
        if (boolean){
            button.visibility = View.INVISIBLE
        }

        button.setOnClickListener {
            REF_DATABASE_ROOT.removeValue()
                .addOnSuccessListener {
                    showToast("Deleted")
                    replaceFragment(SavedFragment())
                }
                .addOnFailureListener {
                   showToast("Check your connection!")
                }
        }
    }
}