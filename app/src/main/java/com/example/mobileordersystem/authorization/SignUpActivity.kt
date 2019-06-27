package com.example.mobileordersystem.authorization

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import com.example.mobileordersystem.HomeActivity
import com.example.mobileordersystem.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.sign_up.*
import com.google.firebase.auth.UserProfileChangeRequest




class SignUpActivity : AbstractValidation() {
    private lateinit var mAuth: FirebaseAuth
    private val TAG = "FirebaseEmailPassword"
    private lateinit var email: String
    private lateinit var password: String
    private lateinit var name: String
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)
        mAuth = FirebaseAuth.getInstance()
        findViewById<Button>(R.id.signUp).setOnClickListener {
            signUp()
        }
    }

    fun signUp() {

        email = findViewById<EditText>(R.id.emailInput).text.toString()
        password = findViewById<EditText>(R.id.passwordInput).text.toString()
        name=findViewById<EditText>(R.id.nameInput).text.toString()
        if (!validateForm(email, password,signUp)) {
            return
        }
        createAccount()
    }

    private fun createAccount() {
        progressBar2.visibility = View.VISIBLE

        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    val user = mAuth.currentUser
                    val profileUpdates = UserProfileChangeRequest.Builder()
                        .setDisplayName(name).build()
                    user!!.updateProfile(profileUpdates);
                    Log.e(TAG, "createAccount: Success!")
                    sendEmailVerification()
                } else {
                    Log.e(TAG, "createAccount: Fail!", task.exception)
                    findViewById<ProgressBar>(R.id.progressBar).visibility=View.GONE
                    Toast.makeText(applicationContext, "Authentication failed!", Toast.LENGTH_SHORT).show()
                }
            }
    }

    fun continueSignUp(view: View) {
        FirebaseAuth.getInstance().currentUser?.reload()?.addOnSuccessListener { void ->
            val user = FirebaseAuth.getInstance().currentUser
            if (user != null && user.isEmailVerified) {
                val intent = Intent(this@SignUpActivity, HomeActivity::class.java)
                startActivity(intent)
                finish()
            }else if(!user!!.isEmailVerified){
                Toast.makeText(applicationContext, R.string.notVerified, Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendEmailVerification() {
        val user = mAuth.currentUser
        user!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    setContentView(R.layout.check_email)
                    Toast.makeText(applicationContext, "Verification email sent to " + user.email!!, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    findViewById<ProgressBar>(R.id.progressBar).visibility=View.GONE
                    Toast.makeText(applicationContext, "Failed to send verification email.", Toast.LENGTH_SHORT).show()
                }
            }
    }
    override fun onBackPressed() {
        finish()
        super.onBackPressed()
    }


}