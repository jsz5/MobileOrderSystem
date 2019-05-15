package com.example.mobileordersystem.equipment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mobileordersystem.HomeActivity
import com.example.mobileordersystem.MainActivity
import com.example.mobileordersystem.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_equipment.*
import kotlinx.android.synthetic.main.fragment_equipment.view.*

class EquipmentFragment: Fragment() {

    val TAG = "EquipmentFragment"
    lateinit var myAdapter: EquipmentAdapter
    val equipmentList: MutableList<Equipment> = mutableListOf()
    val databaseReference = FirebaseDatabase.getInstance().reference

    companion object {
        fun newInstance(): EquipmentFragment = EquipmentFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_equipment, container, false)

    }

    fun setAdapter(context : Context){

        myAdapter = EquipmentAdapter(equipmentList, context)
        view?.eqContainer?.adapter = myAdapter
        getEquipmentList()
    }


    private fun getEquipmentList() {
        AsyncTask.execute {
            val equipmentListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    equipmentList.clear()
                    dataSnapshot.children.mapNotNullTo(equipmentList) { it.getValue<Equipment>(Equipment::class.java) }
                    myAdapter?.notifyDataSetChanged()
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadPost:onCancelled ${databaseError.toException()}")
                }
            }
            databaseReference.child("Equipment").addListenerForSingleValueEvent(equipmentListener)
        }
    }

    private fun createEquipment(name: String, amount: Int, price: Float) {
        AsyncTask.execute {
            val equipmentReference = FirebaseDatabase.getInstance().getReference("Equipment")
            val id = equipmentReference.push().key as String
            val equipment = Equipment(id, name, amount, price)
            equipmentReference.child(id).setValue(equipment);
        }
    }
}