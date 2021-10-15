package com.example.bettingstrategies.activites

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.bettingstrategies.R
import com.example.bettingstrategies.UI.fragments.GoogleLogin
import com.example.bettingstrategies.utilits.initFirebase
import com.example.bettingstrategies.utilits.replaceFragment

class RegisterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register)

        replaceFragment(GoogleLogin())
        initFirebase()
    }

    companion object {
        fun getLaunchIntent(from: Context) = Intent(from, RegisterActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK)
        }
    }
}