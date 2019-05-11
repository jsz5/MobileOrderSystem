package com.example.mobileordersystem.Utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.design.widget.BottomNavigationView
import android.util.Log
import com.example.mobileordersystem.HomeActivity
import com.example.mobileordersystem.R
import com.example.mobileordersystem.equipment.EquipmentActivity
import com.example.mobileordersystem.order.OrdersActivity
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx
import java.security.AccessControlContext


class BottomNavigationViewHelper {

    companion object {
        const val TAG = "BottomNavigationViewHel"

        fun setupBottomNavigationView(bottomNavigationViewEx: BottomNavigationViewEx) {
            Log.d(TAG, "setupBottomNavigationView: Setting up BottomNavigationView")
            bottomNavigationViewEx.enableAnimation(false)
            bottomNavigationViewEx.enableShiftingMode(false)
            bottomNavigationViewEx.enableItemShiftingMode(false)
            bottomNavigationViewEx.setTextVisibility(false)
        }


        fun enableNavigation(context: Context, view : BottomNavigationViewEx) {
            view.onNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
                when(menuItem.itemId) {
                    R.id.ic_equipment -> {
                        val intent1 = Intent(context, EquipmentActivity::class.java)
                        context.startActivity(intent1)
                        true
                    }
                    R.id.ic_orders -> {
                        val intent2 = Intent(context, OrdersActivity::class.java)
                        context.startActivity(intent2)
                        true
                    }
                    R.id.ic_home -> {
                        val intent3 = Intent(context, HomeActivity::class.java)
                        context.startActivity(intent3)
                        true
                    }
                    else -> false
                }
            }
        }


    }

}