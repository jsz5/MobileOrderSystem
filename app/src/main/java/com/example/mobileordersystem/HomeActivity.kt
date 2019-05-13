package com.example.mobileordersystem

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Toast
import com.example.mobileordersystem.authorization.LoginActivity
import com.example.mobileordersystem.customer.CustomerFragment
import com.example.mobileordersystem.equipment.EquipmentFragment
import com.example.mobileordersystem.order.OrderFragment
import com.firebase.ui.auth.AuthUI


class HomeActivity : AppCompatActivity() {


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                val homeFragment = HomeFragment.newInstance()
                openFragment(homeFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_equipment -> {
                val equipmentFragment = EquipmentFragment.newInstance()
                openFragment(equipmentFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_orders -> {
                val orderFragment = OrderFragment.newInstance()
                openFragment(orderFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_customers -> {
                val customerFragment = CustomerFragment.newInstance()
                openFragment(customerFragment)
                return@OnNavigationItemSelectedListener true
            }
            else -> {
                Toast.makeText(this, "Navigation Error", Toast.LENGTH_SHORT).show()
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        val homeItem = navView.menu.getItem(0)
        homeItem.isChecked = true
        onNavigationItemSelectedListener.onNavigationItemSelected(homeItem)

    }

    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    fun logout(view: View) {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }

    }


}


