package com.example.mobileordersystem.authorization

import android.text.TextUtils
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.sign_in.*

abstract class AbstractValidation: AppCompatActivity() {
    protected fun validateForm(email: String, password: String, view: View): Boolean {

        if (TextUtils.isEmpty(email)) {
            Snackbar.make(view, "Enter email address!", Snackbar.LENGTH_LONG).show()
            return false
        }
        if (TextUtils.isEmpty(password)) {
            Snackbar.make(view, "Enter password!", Snackbar.LENGTH_LONG).show()
            return false
        }

        if (password.length < 6) {
            Snackbar.make(view, "Password too short, enter minimum 6 characters!", Snackbar.LENGTH_LONG).show()
            return false
        }

        return true
    }

}