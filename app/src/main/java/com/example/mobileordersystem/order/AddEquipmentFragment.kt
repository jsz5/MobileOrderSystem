package com.example.mobileordersystem.order

import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.Spinner
import com.example.mobileordersystem.R
import com.example.mobileordersystem.customer.CustomerFragment
import com.example.mobileordersystem.equipment.Equipment
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_create_order.*
import kotlinx.android.synthetic.main.fragment_order.*
import kotlinx.android.synthetic.main.fragment_plus_equipment.*




class AddEquipmentFragment : androidx.fragment.app.Fragment() {
    private val equipmentList: MutableList<Equipment> = mutableListOf()
    private lateinit var databaseReference: DatabaseReference
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        databaseReference = FirebaseDatabase.getInstance().reference
        return inflater.inflate(R.layout.fragment_plus_equipment, container, false)
    }

    companion object {
        fun newInstance(): AddEquipmentFragment = AddEquipmentFragment()
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        setEquipmentSpinner()
//        setAddEquipment()

    }

//    private fun setEquipmentSpinner() {
//        AsyncTask.execute {
//            val equipmentListener = object : ValueEventListener {
//                override fun onDataChange(dataSnapshot: DataSnapshot) {
//                    equipmentList.clear()
//                    dataSnapshot.children.mapNotNullTo(equipmentList) { it.getValue<Equipment>(Equipment::class.java) }
//                    val spinner: Spinner = equipmentInput
//                    val spinnerArrayAdapter =
//                        ArrayAdapter<Equipment>(activity, android.R.layout.simple_spinner_item, equipmentList)
//
//                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//                    spinner.adapter = spinnerArrayAdapter
//                    spinnerArrayAdapter.notifyDataSetChanged()
//                }
//
//                override fun onCancelled(databaseError: DatabaseError) {
//                    println("loadPost:onCancelled ${databaseError.toException()}")
//                }
//            }
//            databaseReference.child("Equipment").addListenerForSingleValueEvent(equipmentListener)
//
//        }
//    }
////
//    private fun setAddEquipment() {
//        addEquipment.setOnClickListener {
//
//
//            val ll = LinearLayout(activity)
//            ll.orientation = LinearLayout.HORIZONTAL
//            fragmentManager!!.beginTransaction().add(ll.id, AddEquipmentFragment())
//            newSpinner.addView(ll)
////            val child = layoutInflater.inflate(R.layout., null)
////            layout.addView(child)
////
////
////
////            val txt1 = TextView(this@MyClass)
////            linearLayout.setBackgroundColor(Color.TRANSPARENT)
////            linearLayout.addView(txt1)
//
//        }
//    }
}