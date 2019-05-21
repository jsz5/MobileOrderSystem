package com.example.mobileordersystem.order

import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.fragment.app.FragmentManager
import com.example.mobileordersystem.R
import com.example.mobileordersystem.customer.Customer
import com.example.mobileordersystem.equipment.Equipment
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_create_order.*





class CreateOrderFragment: androidx.fragment.app.Fragment() {
    private   val customerList: MutableList<Customer> = mutableListOf()
    private lateinit var databaseReference: DatabaseReference
    companion object {
        fun newInstance(): CreateOrderFragment = CreateOrderFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_order, container, false)

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        databaseReference = FirebaseDatabase.getInstance().reference
        super.onActivityCreated(savedInstanceState)
        setDatePickers()
        setCustomerSpinner()
    }
    private fun setDatePickers(){
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
    private fun setCustomerSpinner(){
        getCustomerList()
        var customersNames= arrayListOf<String>()
        Log.i("drugi", customerList[0].name)
        for (customer in customerList){
            customersNames.add(customer.name+" "+customer.surname)
        }
        val colors = arrayListOf<String>("Red", "Blue", "White", "Yellow", "Black", "Green", "Purple", "Orange", "Grey")
        val spinner: Spinner = customerInput
        val spinnerArrayAdapter = ArrayAdapter<String>(activity, android.R.layout.simple_spinner_item, customersNames)
        spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item) // The drop down view
        spinner.adapter = spinnerArrayAdapter

    }
    private fun getCustomerList() {
        AsyncTask.execute {
            val equipmentListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    customerList.clear()
                    dataSnapshot.children.mapNotNullTo(customerList) { it.getValue<Customer>(Customer::class.java) }
                    Log.i("pierwszy", customerList[0].name)
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadPost:onCancelled ${databaseError.toException()}")
                }
            }
            databaseReference.child("Customer").addListenerForSingleValueEvent(equipmentListener)
        }
    }

}