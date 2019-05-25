package com.example.mobileordersystem.equipment

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobileordersystem.R
import kotlinx.android.synthetic.main.activity_show_equipment.*

class ShowEquipmentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_equipment)

        val extras = intent.extras
        val name = extras.getString("name")
        val amount = extras.getInt("amount")
        val price = extras.getFloat("price")

        nameInput.setText(name)
        amountInput.setText(amount.toString())
        priceInput.setText(price.toString())

    }
}
