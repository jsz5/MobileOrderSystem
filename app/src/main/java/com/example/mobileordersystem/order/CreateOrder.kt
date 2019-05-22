package com.example.mobileordersystem.order

import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobileordersystem.R
import com.example.mobileordersystem.customer.CreateCustomer
import com.example.mobileordersystem.customer.Customer
import com.example.mobileordersystem.equipment.Equipment
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_create_order.*

import android.view.View
import android.widget.*


class CreateOrder : AppCompatActivity() {
    private val customerList: MutableList<Customer> = mutableListOf()
    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_create_order)
        addCustomer.setOnClickListener {
            val intent = Intent(this, CreateCustomer::class.java)
            startActivity(intent)
        }
        databaseReference = FirebaseDatabase.getInstance().reference
        setDatePickers()
        setCustomerSpinner()
        addEquipment.setOnClickListener {

            val layout = newSpinner as LinearLayout
            val addSpinner=findViewById<Spinner>(R.id.equipmentInput)
            val newSpinner=Spinner(this)
            layout.addView(Spinner(this))

        }


    }

    private fun setDatePickers() {
        rentalDateInput.setOnClickListener {
            val newFragment = DatePicker()
            val bundle = Bundle()
            bundle.putString("dateButton", "rental")
            newFragment.arguments = bundle
            newFragment.show(supportFragmentManager, "datePicker")
        }
        returnDateInput.setOnClickListener {
            val newFragment = DatePicker()
            val bundle = Bundle()
            bundle.putString("dateButton", "return")
            newFragment.arguments = bundle
            newFragment.show(supportFragmentManager, "datePicker")
        }
    }

    private fun setCustomerSpinner() {
        AsyncTask.execute {
            val customerListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    customerList.clear()
                    dataSnapshot.children.mapNotNullTo(customerList) { it.getValue<Customer>(Customer::class.java) }
                    val spinner: Spinner = customerInput
                    val spinnerArrayAdapter =
                        ArrayAdapter<Customer>(this@CreateOrder, android.R.layout.simple_spinner_item, customerList)

                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = spinnerArrayAdapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadPost:onCancelled ${databaseError.toException()}")
                }
            }
            databaseReference.child("Customer").addListenerForSingleValueEvent(customerListener)
        }
    }





}


