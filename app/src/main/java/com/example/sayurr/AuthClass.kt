package com.example.sayurr

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.firestore.FirebaseFirestore

internal class AuthClass(context: Context) {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDB : FirebaseFirestore
    private lateinit var user : FirebaseUser
    private var userData : Bundle = Bundle()
    private val context = context

    init {
        setFirebase()
        checkUser()
    }

    private fun setFirebase() {
        mAuth = FirebaseAuth.getInstance()
        mDB = FirebaseFirestore.getInstance()
    }

    private fun checkUser() {
        if (mAuth.currentUser == null) {
            val intent = Intent(context, LoginActivity::class.java)
            context.startActivity(intent)

        } else {
            user = mAuth.currentUser!!
        }
    }

    internal fun setDataAndNext(intent: Intent) {
        //val intent = Intent(context, classDes)
        mDB.collection("users").document(user.uid)
            .get()
            .addOnSuccessListener { documentSnapshot ->
                userData = Bundle()
                userData.putString("uid", documentSnapshot.id)
                userData.putString("name", documentSnapshot["name"].toString())
                userData.putString("email", user.email)
                userData.putString("phone", documentSnapshot["phone"].toString())
//                userData.putString("address1", documentSnapshot["address1"].toString())
//                userData.putString("address2", documentSnapshot["address2"].toString())
                intent.putExtra("data", userData)
                context.startActivity(intent)
            }
            .addOnFailureListener { exception ->
                Log.d("UserName", "Error getting documents: ", exception)
            }
    }
}