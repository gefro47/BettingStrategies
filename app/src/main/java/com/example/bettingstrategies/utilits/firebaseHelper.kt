package com.example.bettingstrategies.utilits

import android.content.Context
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.RecyclerView
import com.example.bettingstrategies.models.SavedStrategy
import com.example.bettingstrategies.models.Strategy
import com.example.bettingstrategies.models.User
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_first_launch.*
import kotlinx.android.synthetic.main.fragment_saved.*

lateinit var savedStrategyList: ArrayList<SavedStrategy>
lateinit var strategyList:ArrayList<Strategy>
lateinit var listForRec: ArrayList<Strategy>
lateinit var AUTH: FirebaseAuth
lateinit var REF_DATABASE_ROOT: DatabaseReference
lateinit var USER: User
lateinit var UID: String

const val NODE_STRATEGY = "startegy"
const val NODE_SAVED = "saved"
const val CHILD_IMAGE_URL = "imageUrl"
const val CHILD_NAME = "name"
const val CHILD_TEXT = "text"

const val NODE_USERS = "users"
const val CHILD_ID = "uid"
const val CHILD_EMAIL = "email"


fun initFirebase(){
    AUTH = FirebaseAuth.getInstance()
    REF_DATABASE_ROOT = FirebaseDatabase.getInstance().reference
    USER = User()
    UID = AUTH.currentUser?.uid.toString()
}

fun getStrategyData(boolean: Boolean, rec_view: RecyclerView, context: Context) {


    strategyList = arrayListOf<Strategy>()
    savedStrategyList = arrayListOf<SavedStrategy>()
    listForRec = arrayListOf<Strategy>()

    if(boolean) {
        REF_DATABASE_ROOT = FirebaseDatabase.getInstance().getReference(NODE_STRATEGY)
        REF_DATABASE_ROOT.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val strategy = userSnapshot.getValue(Strategy::class.java)
                        strategyList.add(strategy!!)
                    }
                    initRecyclerView(true, rec_view, context, strategyList)
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showToast("Please, check connection!")
            }
        })
    }else if (!boolean) {
        REF_DATABASE_ROOT =
            FirebaseDatabase.getInstance().getReference(NODE_USERS).child(USER.uid.toString())
                .child(NODE_SAVED)
        REF_DATABASE_ROOT.addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                if (snapshot.exists()) {
                    for (userSnapshot in snapshot.children) {
                        val strategy = userSnapshot.getValue(SavedStrategy::class.java)
//                        strategyList.add(strategy!!)
                        FirebaseDatabase.getInstance().getReference(NODE_STRATEGY)
                            .addValueEventListener(object : ValueEventListener {
                                override fun onDataChange(snapshot: DataSnapshot) {
                                    if (snapshot.exists()) {
                                        for (userSnapshot1 in snapshot.children) {
                                            val strategy1 =
                                                userSnapshot1.getValue(Strategy::class.java)
                                            if (strategy1!!.name == strategy!!.name.toString()) {
                                                listForRec.add(strategy1)
                                            }
                                        }
                                    }
                                    initRecyclerView(
                                        false,
                                        rec_view,
                                        context,
                                        listForRec
                                    )
                                }

                                override fun onCancelled(error: DatabaseError) {
                                    showToast("Please, check connection!")
                                }
                            })
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                showToast("Please, check connection!")
            }
        })
    }
}
