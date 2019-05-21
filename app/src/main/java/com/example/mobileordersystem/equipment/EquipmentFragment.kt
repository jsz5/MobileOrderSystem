package com.example.mobileordersystem.equipment

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobileordersystem.HomeActivity
import com.example.mobileordersystem.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_equipment.*
import kotlinx.android.synthetic.main.fragment_equipment.view.*

class EquipmentFragment: androidx.fragment.app.Fragment() {

    private val TAG = "EquipmentFragment"
    lateinit var myAdapter: EquipmentAdapter
    val equipmentList: MutableList<Equipment> = mutableListOf()
    private val databaseReference = FirebaseDatabase.getInstance().reference

    companion object {
        fun newInstance(): EquipmentFragment = EquipmentFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_equipment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        eqContainer.layoutManager = LinearLayoutManager(activity)
        eqContainer.adapter = myAdapter
        eqContainer.itemAnimator = DefaultItemAnimator()
        addEquipment.setOnClickListener {
            (activity as HomeActivity).openFragment(4, true)
        }

    }

    fun setAdapter(context : Context){
        myAdapter = EquipmentAdapter(equipmentList, context)
        getEquipmentList()
        Log.i(TAG, "set up")
    }


    private fun getEquipmentList() {
        AsyncTask.execute {
            val equipmentListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    equipmentList.clear()
                    dataSnapshot.children.mapNotNullTo(equipmentList) { it.getValue<Equipment>(Equipment::class.java) }
                    Log.i("equipment", equipmentList[0].name)
                    myAdapter.notifyDataSetChanged()
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadPost:onCancelled ${databaseError.toException()}")
                }
            }
            databaseReference.child("Equipment").addListenerForSingleValueEvent(equipmentListener)
        }
    }


}