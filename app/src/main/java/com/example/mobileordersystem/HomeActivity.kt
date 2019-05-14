package com.example.mobileordersystem

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.Toast
import com.example.mobileordersystem.authorization.ChangePasswordActivity
import com.example.mobileordersystem.authorization.LoginActivity
import com.example.mobileordersystem.customer.CustomerFragment
import com.example.mobileordersystem.equipment.EquipmentFragment
import com.example.mobileordersystem.order.OrderFragment
import com.firebase.ui.auth.AuthUI
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_home.*


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
        val drawerToggle:ActionBarDrawerToggle = object : ActionBarDrawerToggle(
            this,
            drawer_layout,
            R.string.drawer_open,
            R.string.drawer_close
        ){
            override fun onDrawerClosed(view:View){
                super.onDrawerClosed(view)
            }
            override fun onDrawerOpened(drawerView: View){
                super.onDrawerOpened(drawerView)
            }
        }
        // Configure the drawer layout to add listener and show icon on toolbar
        drawerToggle.isDrawerIndicatorEnabled = true
        drawer_layout.addDrawerListener(drawerToggle)
        drawerToggle.syncState()
        // Set navigation view navigation item selected listener
        navigation_view.setNavigationItemSelectedListener{
            when (it.itemId){
                R.id.passwordChange -> changePassword()
                R.id.deleteAccount -> showDeleteAccountDialog()
                R.id.logut ->logout()
            }
            // Close the drawer
            drawer_layout.closeDrawer(GravityCompat.START)
            true
        }
    }
    private fun openFragment(fragment: Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
        transaction.replace(R.id.container, fragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }

    private fun logout() {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
    }
    private fun deleteAccount(){
        val user = FirebaseAuth.getInstance().currentUser
        user?.delete()
            ?.addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val intent = Intent(this, LoginActivity::class.java)
                    startActivity(intent)
                }
            }
    }
    private fun changePassword(){
        val intent = Intent(this, ChangePasswordActivity::class.java)
        startActivity(intent)
    }
    private fun showDeleteAccountDialog()
    {
        val builder = AlertDialog.Builder(this@HomeActivity)
        builder.setTitle(R.string.delete_account)
        builder.setMessage(R.string.alertDelete)
        builder.setPositiveButton("Tak"){dialog, which ->
           deleteAccount()
        }
        builder.setNegativeButton("Nie"){dialog, which ->
            dialog.dismiss()
        }
        val dialog: AlertDialog = builder.create()
        dialog.show()
    }


}


