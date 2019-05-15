package com.example.mobileordersystem.authorization

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import android.widget.Toast
import android.util.Log
import android.text.TextUtils
import android.widget.ProgressBar
import com.example.mobileordersystem.HomeActivity
import com.example.mobileordersystem.R
import kotlinx.android.synthetic.main.sign_in.*
import kotlinx.android.synthetic.main.test.*

class SignInActivity : AppCompatActivity() {

    private val TAG = "FirebaseEmailPassword"

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in)
        mAuth = FirebaseAuth.getInstance()
    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user!=null && user.isEmailVerified){
            alreadySigned(user.email.toString(), user.displayName.toString())
        }


    }
    private fun alreadySigned(email: String, displayName:String) {
        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
        intent.putExtra("user", email)
        intent.putExtra("displayName", displayName)
        startActivity(intent)
        finish()
    }
    fun singUp(view: View){
        val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
        startActivity(intent)
    }

    fun signIn(view: View) {
        findViewById<ProgressBar>(R.id.progressBar).visibility=View.VISIBLE
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()
        if (!validateForm(email, password)) {
            return
        }
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && mAuth.currentUser!!.isEmailVerified) {
                    alreadySigned(email, FirebaseAuth.getInstance().currentUser!!.displayName.toString())
                } else {
                    Log.e(TAG, "signIn: Fail!", task.exception)
                    Toast.makeText(applicationContext, "Authentication failed!", Toast.LENGTH_SHORT).show()
                    findViewById<ProgressBar>(R.id.progressBar).visibility=View.GONE
                }
            }
    }

    private fun validateForm(email: String, password: String): Boolean {

        if (TextUtils.isEmpty(email)) {
            Toast.makeText(applicationContext, "Enter email address!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (TextUtils.isEmpty(password)) {
            Toast.makeText(applicationContext, "Enter password!", Toast.LENGTH_SHORT).show()
            return false
        }

        if (password.length < 6) {
            Toast.makeText(applicationContext, "Password too short, enter minimum 6 characters!", Toast.LENGTH_SHORT)
                .show()
            return false
        }

        return true
    }

}