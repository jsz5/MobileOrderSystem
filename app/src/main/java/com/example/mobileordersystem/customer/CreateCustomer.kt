package com.example.mobileordersystem.customer


import android.graphics.Color
import android.os.AsyncTask
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.example.mobileordersystem.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_create_customer.*
import kotlinx.android.synthetic.main.fragment_create_order.nameInput
import kotlinx.android.synthetic.main.fragment_create_order.save

class CreateCustomer : AppCompatActivity()  {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_create_customer)
        save.setOnClickListener {
            try {
                val name = nameInput.text.toString()
                val surname = surnameInput.text.toString()
                val companyName = companyInput.text.toString()
                val nip = nipInput.text.toString().toInt()
                val email = emailInput.text.toString()
                val address = addressInput.text.toString()
                val telephone = telephoneInput.text.toString().toInt()
                createCustomer(name, surname, companyName, nip, email, address, telephone)
                success(findViewById(android.R.id.content))
            } catch (e: NumberFormatException) {
                fail(findViewById(android.R.id.content))
            }
        }

    }

    private fun createCustomer(
        name: String, surname: String, companyName: String,
        NIP: Int, email: String, address: String, telephone: Int
    ) {
        AsyncTask.execute {
            val customerReference = FirebaseDatabase.getInstance().getReference("Customer")
            val id = customerReference.push().key as String
            val customer = Customer(id, arrayListOf(), name, surname, companyName, NIP, email, address, telephone)
            customerReference.child(id).setValue(customer);
        }
    }
    private fun fail(view: View) {
        val snackbar = Snackbar.make(
            view,
            R.string.add_equipment_failure,
            Snackbar.LENGTH_LONG
        )
        snackbar.view.setBackgroundColor(Color.RED)
        snackbar.show()
    }

    private fun success(view: View) {
        val snackbar = Snackbar.make(view, R.string.add_equipment_success, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(Color.GREEN)
        snackbar.show()
    }

}


