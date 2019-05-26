package com.example.mobileordersystem.customer

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mobileordersystem.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_show_customer.*

class ShowCustomerActivity : AppCompatActivity() {

    private val TAG = "ShowCustomerActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_customer)

        val extras = intent.extras
        val customerId = extras.getString("customerId")
        val name = extras.getString("name")
        val surname = extras.getString("surname")
        val companyName = extras.getString("companyName")
        val nip = extras["nip"]
        val email = extras.getString("email")
        val address = extras.getString("address")
        val telephone = extras.getInt("telephone")


        nameInput.setText(name)
        surnameInput.setText(surname)
        companyInput.setText(companyName)
        nipInput.setText(nip.toString())
        emailInput.setText(email)
        addressInput.setText(address)
        telephoneInput.setText(telephone.toString())

        save.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("Customer")


            ref.child(customerId).updateChildren(
                mapOf(
                    "name" to nameInput.text.toString(),
                    "surname" to surnameInput.text.toString(),
                    "companyName" to companyInput.text.toString(),
                    "nip" to nipInput.text.toString().toInt(),
                    "email" to emailInput.text.toString(),
                    "address" to addressInput.text.toString(),
                    "telephone" to telephoneInput.text.toString().toInt()
                )
            )

            finish()
        }

    }
}
