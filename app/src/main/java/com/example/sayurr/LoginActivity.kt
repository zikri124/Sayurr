package com.example.sayurr

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.ktx.Firebase

class LoginActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var mAuth: FirebaseAuth
    private lateinit var mDB : FirebaseFirestore
    private var userData : Bundle = Bundle()
    private lateinit var inputEmail: EditText
    private lateinit var inputPass: EditText
    private lateinit var btnLogin: Button
    private lateinit var btnRegister: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        mAuth = Firebase.auth
        setView()
    }

    override fun onClick(view: View) {
        if (view.id == btnLogin.id) {
            signInCustom()
        }
//        else if (view.id == btnRegister.id) {
//            /*val intent = Intent(this@LoginActivity, RegisterActivity::class.java)
//            startActivity(intent)
//            finishAffinity()*/
//        }
//        else if (view.id == btnLoginGoogle.id) {
//            signInGoogle()
//        }
    }

    private fun setView() {
        inputEmail = findViewById(R.id.user_email)
        inputPass = findViewById(R.id.user_pass)
        btnLogin = findViewById(R.id.button_login)
        btnRegister = findViewById(R.id.button_register)
        //btnLoginGoogle = findViewById(R.id.button_loginGoogle)

        btnLogin.setOnClickListener(this)
        btnRegister.setOnClickListener(this)
        //btnLoginGoogle.setOnClickListener(this)

        mDB = FirebaseFirestore.getInstance()
    }

    private fun signInCustom() {
        if (!validateForm()) {
            return
        }
        val email = inputEmail.text.toString()
        val pass = inputPass.text.toString()
        mAuth.signInWithEmailAndPassword(email, pass)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.d("loginStatus", "signInWithEmail:success")
                    val user = mAuth.currentUser
                    updateUI(user)
                } else {
                    Log.w("loginStatus", "signInWithEmail:failure", task.exception)
                    Toast.makeText(
                        this@LoginActivity, "Failed to Sign in : " + task.exception,
                        Toast.LENGTH_SHORT
                    ).show()
                    updateUI(null)
                }
            }
    }

//    private fun signInGoogle() {
//        TODO("Not yet implemented")
//    }

    private fun validateForm(): Boolean {
        var result = true
        if (inputEmail.text.toString() == "") {
            inputEmail.error = "Required"
            result = false
        }
        if (inputPass.text.toString() == "") {
            inputPass.error = "Required"
            result = false
        }
        return result
    }

    private fun updateUI(user: FirebaseUser?) {
        if (user == null) {
            inputEmail.setText("")
            inputPass.setText("")
        } else {
            val intent = Intent(this@LoginActivity, MainActivity::class.java)

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
}