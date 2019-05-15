package com.example.mobileordersystem.authorization

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.example.mobileordersystem.HomeActivity
import com.example.mobileordersystem.R
import com.google.firebase.auth.EmailAuthProvider
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import kotlinx.android.synthetic.main.sign_in.*

class SignUpActivity : AppCompatActivity() {
    private lateinit var mAuth: FirebaseAuth
    private val TAG = "FirebaseEmailPassword"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.sign_up)
        mAuth = FirebaseAuth.getInstance()
    }
    fun signUp(view: View){
        val email = emailInput.text.toString()
        val password = passwordInput.text.toString()
        createAccount(email, password)

    }
    private fun createAccount(email: String, password: String) {

        mAuth!!.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener(this) { task ->
                if (task.isSuccessful) {
                    Log.e(TAG, "createAccount: Success!")
                    sendEmailVerification()
                } else {
                    Log.e(TAG, "createAccount: Fail!", task.exception)
                    Toast.makeText(applicationContext, "Authentication failed!", Toast.LENGTH_SHORT).show()
                    updateUI(null)
                }
            }
    }
    fun continueSignUp(view: View){
        val user = mAuth.currentUser
        if(user!=null) {
            mAuth.signOut()
        }
        if(user!!.isEmailVerified){
            val intent = Intent(this@SignUpActivity, HomeActivity::class.java)
            startActivity(intent)
        }else{
            Toast.makeText(applicationContext, R.string.notVerified, Toast.LENGTH_SHORT).show()
        }
    }

    private fun sendEmailVerification() {
        // Disable Verify Email button
        val user = mAuth.currentUser
        user!!.sendEmailVerification()
            .addOnCompleteListener(this) { task ->
                // Re-enable Verify Email button
                if (task.isSuccessful) {
                  setContentView(R.layout.check_email)
                    Toast.makeText(applicationContext, "Verification email sent to " + user.email!!, Toast.LENGTH_SHORT)
                        .show()
                } else {
                    Log.e(TAG, "sendEmailVerification failed!", task.exception)
                    Toast.makeText(applicationContext, "Failed to send verification email.", Toast.LENGTH_SHORT).show()
                }
            }
    }
    private fun updateUI(user: FirebaseUser?) {

        if (user != null) {
            Toast.makeText(applicationContext, "Signed in!" + user.isEmailVerified, Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(applicationContext, "Signed out!", Toast.LENGTH_SHORT).show()
        }
    }


}