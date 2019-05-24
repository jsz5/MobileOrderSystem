package com.example.mobileordersystem

import android.graphics.Color
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.snackbar.Snackbar

abstract class AbstractDataUpdate : AppCompatActivity() {
    protected fun fail(view: View, message: Int) {
        val snackbar = Snackbar.make(
            view,
            message,
            Snackbar.LENGTH_LONG
        )
        snackbar.view.setBackgroundColor(Color.RED)
        snackbar.show()
    }

    protected fun success(view: View) {
        val snackbar = Snackbar.make(view, R.string.add_equipment_success, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(Color.GREEN)
        snackbar.show()
    }
}