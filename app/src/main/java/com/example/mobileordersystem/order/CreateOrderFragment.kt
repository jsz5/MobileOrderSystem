package com.example.mobileordersystem.order

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentManager
import com.example.mobileordersystem.R
import kotlinx.android.synthetic.main.fragment_create_order.*

class CreateOrderFragment: androidx.fragment.app.Fragment() {
    companion object {
        fun newInstance(): CreateOrderFragment = CreateOrderFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_order, container, false)

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        rentalDateInput.setOnClickListener {
            val newFragment = DatePicker()
            val bundle = Bundle()
            bundle.putString("dateButton", "rental")
            newFragment.arguments=bundle
            newFragment.show(fragmentManager as FragmentManager, "datePicker")
        }
        returnDateInput.setOnClickListener {
            val newFragment = DatePicker()
            val bundle = Bundle()
            bundle.putString("dateButton", "return")
            newFragment.arguments=bundle
            newFragment.show(fragmentManager as FragmentManager, "datePicker")
        }
    }

}