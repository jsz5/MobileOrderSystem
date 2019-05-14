package com.example.mobileordersystem.equipment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mobileordersystem.R
import kotlinx.android.synthetic.main.fragment_equipment.*

class EquipmentFragment: Fragment() {

    val TAG = "EquipmentFragment"

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_equipment, container, false)

    companion object {
        fun newInstance(): EquipmentFragment = EquipmentFragment()
    }

    fun click() {
        textview.text = " CLICKED "
    }

}