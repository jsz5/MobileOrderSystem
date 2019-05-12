package com.example.mobileordersystem.equipment

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mobileordersystem.R

class EquipmentFragment: Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_equipment, container, false)

    companion object {
        fun newInstance(): EquipmentFragment = EquipmentFragment()
    }
}