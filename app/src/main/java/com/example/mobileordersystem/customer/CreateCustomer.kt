package com.example.mobileordersystem.customer


import android.os.AsyncTask
import android.os.Bundle
import android.widget.EditText
import android.widget.TextView
import com.example.mobileordersystem.AbstractDataUpdate
import com.example.mobileordersystem.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_create_customer.*

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
                success(linearLayout)
                finish()
            } catch (e: NumberFormatException) {
                fail(linearLayout,  R.string.failure)
            }
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        for(i in 0..linearLayout.childCount) {
            val view = linearLayout.getChildAt(i)
            if(view is EditText) {
                outState.putString(view.id.toString(), view.text.toString())
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        for(i in 0..linearLayout.childCount) {
            val view = linearLayout.getChildAt(i)
            if(view is EditText) {
                val text = savedInstanceState?.getString(view.id.toString())
                view.setText(text)
            }
        }
    }

    private fun createCustomer(customer: Customer) {
        AsyncTask.execute {
            val customerReference = FirebaseDatabase.getInstance().getReference("Customer")
            val customerId = customerReference.push().key as String
            customer.customerId = customerId
            customerReference.child(customerId).setValue(customer)
        }
    }
}


