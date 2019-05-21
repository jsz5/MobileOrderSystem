package com.example.mobileordersystem.equipment

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

class CreateEquipmentFragment : androidx.fragment.app.Fragment() {
    companion object {
        fun newInstance(): CreateEquipmentFragment = CreateEquipmentFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_create_equipment, container, false)

    }
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        save.setOnClickListener {
            try {
                createEquipment(
                    nameInput.text.toString(),
                    amountInput.text.toString().toInt(),
                    priceInput.text.toString().toFloat()
                )
            }catch (e: NumberFormatException){
                fail()
            }
        }
    }
    private fun createEquipment(name: String, amount: Int, price: Float) {

        AsyncTask.execute {
            try {
                val equipmentReference = FirebaseDatabase.getInstance().getReference("Equipment")
                val id = equipmentReference.push().key as String
                val equipment = Equipment(id, name, amount, price)
                equipmentReference.child(id).setValue(equipment)
                val snackbar = Snackbar.make(activity!!.findViewById(android.R.id.content), R.string.add_equipment_success, Snackbar.LENGTH_LONG)
                snackbar.view.setBackgroundColor(Color.GREEN)
                snackbar.show()
                (activity as HomeActivity).openFragment(1, false)
            }catch(e: Error){
                fail()
            }
        }
    }
    private fun fail(){
        val snackbar =Snackbar.make(activity!!.findViewById(android.R.id.content), R.string.add_equipment_failure, Snackbar.LENGTH_LONG)
        snackbar.view.setBackgroundColor(Color.RED)
        snackbar.show()
    }
}