package com.example.mobileordersystem

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.mobileordersystem.authorization.LoginActivity
import com.example.mobileordersystem.customer.CustomerFragment
import com.example.mobileordersystem.equipment.EquipmentFragment
import com.example.mobileordersystem.order.OrderFragment
import com.firebase.ui.auth.AuthUI
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    val TAG = "HomeActivity"

    var currentid = 0
    val fragments = ArrayList<Fragment>()
    val fragmentStack = ArrayList<Int>()


    private val onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.nav_home -> {
                openFragment(0, true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_equipment -> {
                openFragment(1, true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_orders -> {
                openFragment(2, true)
                return@OnNavigationItemSelectedListener true
            }
            R.id.nav_customers -> {
                openFragment(3, true)
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

        fragments.add(HomeFragment.newInstance())
        fragments.add(EquipmentFragment.newInstance())
        fragments.add(OrderFragment.newInstance())
        fragments.add(CustomerFragment.newInstance())

        val transaction = supportFragmentManager.beginTransaction()
        for (fragment in fragments) {
            transaction.add(R.id.container, fragment)
            transaction.hide(fragment)
        }
        transaction.commit()

        navView.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener)
        val homeItem = navView.menu.getItem(0)
        homeItem.isChecked = true
        onNavigationItemSelectedListener.onNavigationItemSelected(homeItem)

    }

    private fun openFragment(id: Int, addToStack : Boolean) {

        if(addToStack) {
            if(fragmentStack.contains(currentid)) {
                fragmentStack.remove(currentid)
            }
            fragmentStack.add(currentid)
        }
        if(fragmentStack.contains(id)) {
            fragmentStack.remove(id)
        }
        Log.d(TAG, fragmentStack.toString())

        doReplaceTransaction(fragments[currentid], fragments[id])

        currentid = id
    }

    private fun doReplaceTransaction(toReplace : Fragment, replacement : Fragment) {
        val transaction = supportFragmentManager.beginTransaction()
//        transaction.replace(R.id.container, fragment)
//        transaction.addToBackStack(null)
        transaction.hide(toReplace)
        transaction.show(replacement)
        transaction.commit()
    }



    override fun onBackPressed() {
        if(fragmentStack.isEmpty()) {
            if(currentid == 0) {
                super.onBackPressed()
            } else {
                openFragment(0,false)
                val navItem = nav_view.menu.getItem(0)
                navItem.isChecked = true
            }
        } else {
            Log.d(TAG, fragmentStack.toString())
            val backId = fragmentStack.last()
            fragmentStack.removeAt(fragmentStack.size-1)

            openFragment(backId, false)
            val navItem = nav_view.menu.getItem(backId)
            navItem.isChecked = true

            Log.d(TAG, fragmentStack.toString())
        }
//        Log.d(TAG,supportFragmentManager.getBackStackEntryAt(0).name)

    }

    fun logout(view: View) {
        AuthUI.getInstance()
            .signOut(this)
            .addOnCompleteListener {
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish()
            }
        finish()
    }

    fun onClick(view: View) {
        if(currentid == 1) {
            (fragments[currentid] as EquipmentFragment).click()
        }
    }


}


