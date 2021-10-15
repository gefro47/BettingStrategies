package com.example.bettingstrategies.UI.fragments

import android.content.Intent
import android.util.Log
import androidx.fragment.app.Fragment
import com.example.bettingstrategies.MainActivity
import com.example.bettingstrategies.R
import com.example.bettingstrategies.activites.RegisterActivity
import com.example.bettingstrategies.utilits.*
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.android.synthetic.main.fragment_google_login.*

class GoogleLogin : Fragment(R.layout.fragment_google_login) {
    private val RC_SIGN_IN: Int = 1000
    lateinit var mGoogleSignInClient: GoogleSignInClient
    lateinit var mGoogleSignInOptions: GoogleSignInOptions
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onStart() {
        super.onStart()
        firebaseAuth = FirebaseAuth.getInstance()


        mGoogleSignInOptions = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        mGoogleSignInClient = GoogleSignIn.getClient(APP_ACTIVITY, mGoogleSignInOptions)

        Signin.setOnClickListener { signIn() }

        val user = FirebaseAuth.getInstance().currentUser
        if (user != null) {
//            showToast("лул")
            startActivity(MainActivity.getLaunchIntent(APP_ACTIVITY.applicationContext))
            RegisterActivity().finish()
        }

        Log.d("login", user.toString())
    }

    private fun signIn() {
        val signInIntent: Intent = mGoogleSignInClient.signInIntent
        startActivityForResult(signInIntent, RC_SIGN_IN)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == RC_SIGN_IN) {
            val task: Task<GoogleSignInAccount> = GoogleSignIn.getSignedInAccountFromIntent(data)
            try {
                val account = task.getResult(ApiException::class.java)
                if (account != null) {
                    firebaseAuthWithGoogle(account)
                }
            } catch (e: ApiException) {
                showToast("Google sign in failed:(")
                showToast("$e")
                Log.d("kek", "$e")
            }
        }
    }

    private fun firebaseAuthWithGoogle(acct: GoogleSignInAccount) {
        val credential = GoogleAuthProvider.getCredential(acct.idToken, null)
        firebaseAuth.signInWithCredential(credential)
            .addOnCompleteListener(RegisterActivity()) {
                if (it.isSuccessful) {
                    val uid = AUTH.currentUser?.uid.toString()
                    val email = acct.email.toString()
                    var dateMap = mutableMapOf<String, Any>()
                    dateMap[CHILD_ID] = uid
                    dateMap[CHILD_EMAIL] = email
                    REF_DATABASE_ROOT
                        .child(NODE_USERS)
                        .child(uid)
                        .updateChildren(dateMap)
                        .addOnCompleteListener {task2 ->
                            if (task2.isSuccessful){
                                startActivity(MainActivity.getLaunchIntent(APP_ACTIVITY.applicationContext))
                                RegisterActivity().finish()
                                showToast("Добро пожаловать!")
                                Log.d("acc", FirebaseAuth.getInstance().uid.toString())
                            }else showToast(task2.exception?.message.toString())
                        }
                } else {
                    showToast("Google sign in failed:( kek ${it.exception}")
                    Log.d("kek", "${it.exception}")
                }
            }
    }
}
