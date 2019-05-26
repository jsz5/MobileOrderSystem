package com.example.mobileordersystem.customer

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileordersystem.HomeActivity
import com.example.mobileordersystem.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_customer.*
import kotlinx.android.synthetic.main.fragment_customer.addCustomer

class CustomerFragment : androidx.fragment.app.Fragment() {

    private val TAG = "CustomerFragment"
    lateinit var myAdapter: CustomerAdapter
    val customerList: MutableList<Customer> = mutableListOf()
    var customerListCopy: MutableList<Customer> = mutableListOf()
    private val databaseReference = FirebaseDatabase.getInstance().reference
    var searchPattern: String = ""

    companion object {
        fun newInstance(): CustomerFragment = CustomerFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_customer, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mContext = context as Context
        myAdapter = CustomerAdapter(customerList, mContext)
        getCustomerList()
        customersContainer.layoutManager = LinearLayoutManager(context)
        customersContainer.adapter = myAdapter
        customersContainer.itemAnimator = DefaultItemAnimator()

        customersContainer.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    addCustomer.shrink(true)
                } else {
                    addCustomer.extend(true)
                }
            }
        })

//        materialToolbar.setNavigationOnClickListener { (activity as HomeActivity).openDrawer() }
        addCustomer.setOnClickListener {
            val intent = Intent(activity, CreateCustomer::class.java)
            startActivity(intent)
        }

        menuTrigger.setOnClickListener {
            (activity as HomeActivity).openDrawer()
        }

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                search()
            }

        })
    }

    private fun search() {
        if(searchInput != null) {
            searchPattern = searchInput.text.toString()
            if (searchPattern.isBlank()) {
                customerList.clear()
                for (customer in customerListCopy) {
                    customerList.add(customer)
                }
            } else {
                customerList.clear()
                for (customer in customerListCopy) {
                    customerList.add(customer)
                    val name = "${customer.name} ${customer.surname}"
                    if (!name.contains(searchPattern, true)) {
                        customerList.remove(customer)
                    }
                }
            }
            myAdapter.notifyDataSetChanged()
        }
    }

    private fun copyCustomers() {
        customerListCopy.clear()
        for(customer in  customerList) {
            customerListCopy.add(customer)
        }
    }


    private fun getCustomerList() {
        AsyncTask.execute {
            val customerListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    customerList.clear()
                    dataSnapshot.children.mapNotNullTo(customerList) { it.getValue<Customer>(Customer::class.java) }
                    Log.i("customer", customerList[0].name)
                    copyCustomers()
                    search()
                    myAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadPost:onCancelled ${databaseError.toException()}")
                }
            }
            databaseReference.child("Customer").addListenerForSingleValueEvent(customerListener)
        }
    }


}