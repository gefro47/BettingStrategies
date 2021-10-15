package com.example.bettingstrategies.utilits

import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.bettingstrategies.R

fun AppCompatActivity.replaceActivity(activity: AppCompatActivity){
    val intent = Intent(this, activity::class.java)
    startActivity(intent)
    this.finish()
}

fun AppCompatActivity.replaceFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction()
            .replace(
                R.id.dataContainer,
                fragment,
                "fragment"
            ).commit()
    fragment.onDestroy()
}

fun Fragment.replaceFragment(fragment: Fragment){
    this.fragmentManager?.beginTransaction()
        ?.replace(
            R.id.dataContainer,
            fragment
        )?.commit()
}

fun showToast(message: String){
    Toast.makeText(APP_ACTIVITY, message, Toast.LENGTH_SHORT).show()
}