package com.example.mobileordersystem.equipment

import android.graphics.Color
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.mobileordersystem.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_create_equipment.*
import java.lang.Error

class CreateEquipment : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_create_equipment)
        save.setOnClickListener {
            try {
                createEquipment(
                    nameInput.text.toString(),
                    amountInput.text.toString().toInt(),
                    priceInput.text.toString().toFloat()
                )
            } catch (e: NumberFormatException) {
                fail()
            }
        }

    }

    private fun createEquipment(name: String, amount: Int, price: Float) {

        AsyncTask.execute {
            try {
                val equipmentReference = FirebaseDatabase.getInstance().getReference("Equipment")
                val id = equipmentReference.push().key as String
                val equipment = Equipment(id, name, amount,amount, price)
                equipmentReference.child(id).setValue(equipment)
                success()
                finish()
            } catch (e: Error) {
                fail()
            }
        }
    }

    private fun success() {
        val snackbar = Snackbar.make(
            findViewById(android.R.id.content),
            R.string.add_equipment_success,
            Snackbar.LENGTH_LONG
        )
        snackbar.view.setBackgroundColor(Color.GREEN)
        snackbar.show()
    }

    private fun fail() {
        val snackbar =
            Snackbar.make(
                findViewById(android.R.id.content),
                R.string.add_equipment_failure,
                Snackbar.LENGTH_LONG
            )
        snackbar.view.setBackgroundColor(Color.RED)
        snackbar.show()
    }


}


