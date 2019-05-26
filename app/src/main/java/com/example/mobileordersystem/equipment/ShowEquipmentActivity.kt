package com.example.mobileordersystem.equipment

import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import com.example.mobileordersystem.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.activity_show_equipment.*

class ShowEquipmentActivity : AppCompatActivity() {

    private val TAG = "ShowEquipmentActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_equipment)

        val extras = intent.extras
        val id = extras.getString("id")
        val name = extras.getString("name")
        val amount = extras.getInt("amount")
        val amountLeft = extras.getInt("amountLeft")
        val price = extras.getFloat("price")

        nameInput.setText(name)
        amountInput.setText(amount.toString())
        priceInput.setText(price.toString())

        save.setOnClickListener {
            val ref = FirebaseDatabase.getInstance().getReference("Equipment")

            Log.i(TAG, id)
            val newAmountLeft = amountLeft + (amountInput.text.toString().toInt() - amount)

            ref.child(id).updateChildren(
                mapOf(
                    "name" to nameInput.text.toString(),
                    "amount" to amountInput.text.toString().toInt(),
                    "amountLeft" to newAmountLeft,
                    "price" to priceInput.text.toString().toFloat()
                )
            )

            finish()
        }

    }
}
