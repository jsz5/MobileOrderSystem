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
    lateinit var id: String
    lateinit var name: String
    var amount: Int = 0
    var amountLeft: Int = 0
    var price: Float = 0f

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_equipment)

        val extras = intent.extras
        id = extras.getString("id")
        name = extras.getString("name")
        amount = extras.getInt("amount")
        amountLeft = extras.getInt("amountLeft")
        price = extras.getFloat("price")

        nameInput.setText(name)
        amountInput.setText(amount.toString())
        priceInput.setText(price.toString())

    }


    fun save(view: View) {


        val ref = FirebaseDatabase.getInstance().getReference("Equipment")

       Log.i(TAG, id)
        val extras = intent.extras
        amount = extras.getInt("amount")
        amountLeft = extras.getInt("amountLeft")
        val amountLeft = amountLeft + (amountInput.text.toString().toInt() - amount)


        ref.child(id).updateChildren(mapOf(
            "name" to nameInput.text.toString(),
            "amount" to amountInput.text.toString().toInt(),
            "amountLeft" to amountLeft,
            "price" to priceInput.text.toString().toFloat()
        ))

        finish()
    }
}
