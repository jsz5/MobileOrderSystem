package com.example.mobileordersystem.equipment


import android.os.AsyncTask
import android.os.Bundle
import android.widget.EditText
import com.example.mobileordersystem.AbstractDataUpdate
import com.example.mobileordersystem.R
import com.google.firebase.database.FirebaseDatabase
import kotlinx.android.synthetic.main.fragment_create_equipment.*
import java.lang.Error

class CreateEquipment : AbstractDataUpdate() {


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
                fail(findViewById(R.id.content),R.string.failure)
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

    private fun createEquipment(name: String, amount: Int, price: Float) {

        AsyncTask.execute {
            try {
                val equipmentReference = FirebaseDatabase.getInstance().getReference("Equipment")
                val id = equipmentReference.push().key as String
                val equipment = Equipment(id, name, amount,amount, price, arrayListOf())
                equipmentReference.child(id).setValue(equipment)
                success(linearLayout)
                finish()
            } catch (e: Error) {
                fail(content, R.string.failure)
            }
        }
    }




}


