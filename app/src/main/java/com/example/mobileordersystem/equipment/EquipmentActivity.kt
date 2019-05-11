package com.example.mobileordersystem.equipment

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mobileordersystem.R
import com.example.mobileordersystem.Utils.BottomNavigationViewHelper
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.layout_bottom_navigation_view.*

class EquipmentActivity : AppCompatActivity() {

    private val TAG = "EquipmentActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        Log.d(TAG, "onCreate: starting.")

        setupBottomNavigationView()
    }

    /*
    *BottomNavigationView setup
     */
    fun setupBottomNavigationView() {
        Log.d(TAG, "setupBottomNavigationView: setting up BottomNavigationView")
        val bottomNavigationViewEx = bottomNavViewBar
        BottomNavigationViewHelper.setupBottomNavigationView(bottomNavigationViewEx)
        BottomNavigationViewHelper.enableNavigation(this, bottomNavigationViewEx)

        val menu = bottomNavigationViewEx.menu
        val menuItem = menu.getItem(0)
        menuItem.isChecked = true
    }
}
