package com.example.mobileordersystem.customer

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobileordersystem.R
import com.example.mobileordersystem.equipment.Equipment
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_customer.*

class CustomerFragment : androidx.fragment.app.Fragment() {

    private val TAG = "CustomerFragment"
    lateinit var myAdapter: CustomerAdapter
    val customerList: MutableList<Customer> = mutableListOf()
    private val databaseReference = FirebaseDatabase.getInstance().reference

    companion object {
        fun newInstance(): CustomerFragment = CustomerFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_customer, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mContext = context as Context
        myAdapter = CustomerAdapter(customerList,mContext)
        getCustomerList()
        customersContainer.layoutManager = LinearLayoutManager(context)
        customersContainer.adapter = myAdapter
        customersContainer.itemAnimator = DefaultItemAnimator()

    }


    private fun getCustomerList() {
        AsyncTask.execute {
            val equipmentListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    customerList.clear()
                    dataSnapshot.children.mapNotNullTo(customerList) { it.getValue<Customer>(Customer::class.java) }
                    Log.i("customer", customerList[0].name)
                    myAdapter.notifyDataSetChanged()
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadPost:onCancelled ${databaseError.toException()}")
                }
            }
            databaseReference.child("Customer").addListenerForSingleValueEvent(equipmentListener)
        }
    }

}