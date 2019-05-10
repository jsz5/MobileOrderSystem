package com.example.mobileordersystem.Utils

import android.util.Log
import com.ittianyu.bottomnavigationviewex.BottomNavigationViewEx



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

    }

}