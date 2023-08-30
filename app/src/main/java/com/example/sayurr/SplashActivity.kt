package com.example.sayurr

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

class SplashActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDB : FirebaseFirestore
    private var userData : Bundle = Bundle()
    private lateinit var user: FirebaseUser

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        setFirebase()
    }

    override fun onStart() {
        super.onStart()
        Handler().postDelayed({
            checkUser()
            updateUI()
        }, 500)
    }

    private fun checkUser() {
        if (mAuth.currentUser == null) {
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finishAffinity()
        } else {
            user = mAuth.currentUser!!
        }
    }

    private fun setFirebase() {
        mAuth = FirebaseAuth.getInstance()
        mDB = FirebaseFirestore.getInstance()
    }

    private fun updateUI() {
        val intent = Intent(this@SplashActivity, MainActivity::class.java)

        mDB.collection("users").document(user.uid)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                userData = Bundle()
                userData.putString("uid", user.uid)
                userData.putString("name", documentSnapshot["name"].toString())
                userData.putString("email", user.email)
                userData.putString("phone", documentSnapshot["phone"].toString())
//                userData.putString("address1", documentSnapshot["address1"].toString())
//                userData.putString("address2", documentSnapshot["address2"].toString())
                intent.putExtra("data", userData)
                startActivity(intent)
                finishAffinity()
            }
            .addOnFailureListener { exception ->
                Log.d("UserName", "Error getting documents: ", exception)
            }
    }
}