package com.example.mobileordersystem.authorization

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ProgressBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileordersystem.HomeActivity
import com.example.mobileordersystem.R
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.forgot_password.*

class ForgotPasswordActivity : AppCompatActivity() {

    private val TAG = "FirebaseEmailPassword"

    private lateinit var mAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.forgot_password)
        mAuth = FirebaseAuth.getInstance()

    }
    fun sendResetEmail(view: View){
        findViewById<ProgressBar>(R.id.progressBar).visibility = View.VISIBLE
        var resetPassword=findViewById<Button>(R.id.resetPassword)
        mAuth = FirebaseAuth.getInstance()
        resetPassword.setOnClickListener {
            val email = findViewById<EditText>(R.id.emailInput).text.toString().trim()

                mAuth.sendPasswordResetEmail(email)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val intent = Intent(this@ForgotPasswordActivity, SignInActivity::class.java)//
                            startActivity(intent)
                            finish()
                            Toast.makeText(this@ForgotPasswordActivity, "Check email to reset your password!", Toast.LENGTH_SHORT).show()
                        } else {
                            Toast.makeText(this@ForgotPasswordActivity, "Fail to send reset password email!", Toast.LENGTH_SHORT).show()
                        }
                    }


        }

    }
}