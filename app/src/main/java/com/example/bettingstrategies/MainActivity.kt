package com.example.bettingstrategies

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bettingstrategies.UI.fragments.FirstLaunchFragment
import com.example.bettingstrategies.UI.fragments.SavedFragment
import com.example.bettingstrategies.activites.RegisterActivity
import com.example.bettingstrategies.models.User
import com.example.bettingstrategies.utilits.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

lateinit var mGoogleSignInClient: GoogleSignInClient
lateinit var mGoogleSignInOptions: GoogleSignInOptions


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        APP_ACTIVITY = this
        replaceFragment(FirstLaunchFragment())
        initFirebase()
        initUser()
        initFunc()
    }

    @SuppressLint("RestrictedApi")
    override fun onResume() {
        super.onResume()
        exit.setOnClickListener {
            startActivity(RegisterActivity.getLaunchIntent(APP_ACTIVITY.applicationContext))
            FirebaseAuth.getInstance().signOut()
            mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build()
            mGoogleSignInClient = GoogleSignIn.getClient(APP_ACTIVITY, mGoogleSignInOptions)
            mGoogleSignInClient.signOut()
            MainActivity().finish()
        }
        home.setOnClickListener {
            supportFragmentManager.findFragmentById(R.id.dataContainer)?.let {
                if (it is FirstLaunchFragment) {
                }else{
                    replaceFragment(FirstLaunchFragment())
                }
            }
        }
        save.setOnClickListener {
            supportFragmentManager.findFragmentById(R.id.dataContainer)?.let {
                if (it is SavedFragment) {
                }else{
                    replaceFragment(SavedFragment())
                }
            }
        }
    }


    private fun initFunc() {
//        if (true){
        if (AUTH.currentUser != null){
//            replaceFragment(CalendarFragment())
        }else{
            replaceActivity(RegisterActivity())
        }

    }


    private fun initUser() {
        REF_DATABASE_ROOT.child(NODE_USERS).child(UID)
            .addListenerForSingleValueEvent(AppValueEventListener{
                USER = it.getValue(User::class.java) ?: User()
//                showToast(USER.toString())
            })
    }

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, MainActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }
}