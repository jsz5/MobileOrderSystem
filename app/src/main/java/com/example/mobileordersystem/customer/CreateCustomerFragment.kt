package com.example.mobileordersystem.customer

import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mobileordersystem.HomeActivity
import com.example.mobileordersystem.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_create_equipment.*
import java.lang.Error

import android.graphics.Color
import kotlinx.android.synthetic.main.fragment_create_customer.*
import kotlinx.android.synthetic.main.fragment_create_equipment.nameInput
import kotlinx.android.synthetic.main.fragment_create_equipment.save

class CreateCustomerFragment : androidx.fragment.app.Fragment() {
    companion object {
        fun newInstance(): CreateCustomerFragment = CreateCustomerFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_customer, container, false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
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
                success()
            } catch (e: NumberFormatException) {
                fail()
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
    private fun success(){
        val snackbar = Snackbar.make(activity!!.findViewById(android.R.id.content), R.string.add_equipment_success, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(Color.GREEN)
        snackbar.show()
    }
    private fun fail() {
        val snackbar = Snackbar.make(
            activity!!.findViewById(android.R.id.content),
            R.string.add_equipment_failure,
            Snackbar.LENGTH_LONG
        )
        snackbar.view.setBackgroundColor(Color.RED)
        snackbar.show()
    }
}