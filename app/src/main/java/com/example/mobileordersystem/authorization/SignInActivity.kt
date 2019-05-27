package com.example.mobileordersystem.authorization

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.google.android.material.snackbar.Snackbar
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
import android.app.ProgressDialog
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View.OnTouchListener

import android.widget.TextView
import androidx.appcompat.app.AppCompatDelegate


class SignInActivity : AbstractValidation() {

    private val TAG = "FirebaseEmailPassword"

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_in)
        mAuth = FirebaseAuth.getInstance()
        setForgotPassword();
    }

    override fun onStart() {
        super.onStart()
        val user = FirebaseAuth.getInstance().currentUser
        if (user != null && user.isEmailVerified) {
            alreadySigned(user.email.toString(), user.displayName.toString())
        }


    }

    private fun setForgotPassword(){
        val link=findViewById<TextView>(R.id.forgotPassword)
        link.setOnClickListener {
            link.paintFlags= Paint.UNDERLINE_TEXT_FLAG
            val intent = Intent(this@SignInActivity, ForgotPasswordActivity::class.java)
            startActivity(intent)
            finish()

        }
    }
    private fun alreadySigned(email: String, displayName: String) {
        val intent = Intent(this@SignInActivity, HomeActivity::class.java)
        startActivity(intent)
        finish()
    }

    fun singUp(view: View) {
        val intent = Intent(this@SignInActivity, SignUpActivity::class.java)
        startActivity(intent)
    }

    fun signIn(view: View) {
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()
        if (!validateForm(email, password,signIn)) {
            return
        }
        findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful && mAuth.currentUser!!.isEmailVerified) {
                    alreadySigned(email, FirebaseAuth.getInstance().currentUser!!.displayName.toString())
                } else {
                    Log.e(TAG, "signIn: Fail!", task.exception)
                    Toast.makeText(applicationContext, "Authentication failed!", Toast.LENGTH_SHORT).show()
                    findViewById<ProgressBar>(R.id.progressBar).visibility = View.GONE
                }
            }
    }

}