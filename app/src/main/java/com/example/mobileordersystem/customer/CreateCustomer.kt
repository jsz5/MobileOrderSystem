package com.example.mobileordersystem.customer


import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileordersystem.AbstractDataUpdate
import com.example.mobileordersystem.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_create_customer.*
import kotlinx.android.synthetic.main.fragment_create_order.nameInput
import kotlinx.android.synthetic.main.fragment_create_order.save
import java.lang.Exception

class CreateCustomer : AbstractDataUpdate() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_create_customer)
        save.setOnClickListener {
            try {
                val customer = Customer(
                    "", arrayListOf(), nameInput.text.toString(), surnameInput.text.toString(),
                    companyInput.text.toString(), nipInput.text.toString().toInt(), emailInput.text.toString(),
                    addressInput.text.toString(), telephoneInput.text.toString().toInt()
                )
                createCustomer(customer)
                success(findViewById(android.R.id.content))
                finish()
            } catch (e: NumberFormatException) {
                fail(findViewById(android.R.id.content),  R.string.failure)
            }
        }
    }

    private fun createCustomer(customer: Customer) {
        AsyncTask.execute {
            val customerReference = FirebaseDatabase.getInstance().getReference("Customer")
            val customerId = customerReference.push().key as String
            customer.customerId = customerId
            customerReference.child(customerId).setValue(customer);
        }
    }
}


