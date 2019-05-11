package com.example.mobileordersystem

import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mobileordersystem.customer.Customer
import com.example.mobileordersystem.equipment.Equipment
import com.example.mobileordersystem.equipment.EquipmentAdapter
import com.example.mobileordersystem.order.Order
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.collections.ArrayList


class MainActivity : AppCompatActivity() {
    lateinit var databaseReference: DatabaseReference
    private var myAdapter: EquipmentAdapter? = null

    private lateinit var auth: FirebaseAuth
    val equipmentList: MutableList<Equipment> = mutableListOf()
//    private var mAdapter: FirebaseRecyclerAdapter<Equipment, EquipmentViewHolder>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        databaseReference = FirebaseDatabase.getInstance().reference
        myAdapter = EquipmentAdapter(this, equipmentList)
        equipmentListView.adapter = myAdapter
        getEquipmentList()
        updateEquipment("LeYQzZVbncUvj8FdPtG", "update361", 76, 5f)

        //        createEquipment("głośnik", 32, 90.3f)
//        createCustomer(null,"klient","Nazwisko", "Firma",90909090,"mail@gmail.com",
//            "adres 81/9",12129232)
//        var equipmentsId=ArrayList<String>()
//        equipmentsId.add("LeT_GB7TWs1SGY9KRAq")
//        createOrder(equipmentsId, "LeYiH20Qk0IKvsbSwhz", "zamowienie1",
//           "04.04.2019", "16.12.2018",13.1f, 0.34f)

    }


    private fun createEquipment(name: String, amount: Int, price: Float) {
        AsyncTask.execute {
            val equipmentReference = FirebaseDatabase.getInstance().getReference("Equipment")
            val id = equipmentReference.push().key as String
            val equipment = Equipment(id, name, amount, price)
            equipmentReference.child(id).setValue(equipment);
        }
    }

    private fun createCustomer(
        orderId: String? = null, name: String, surname: String, companyName: String,
        NIP: Int, email: String, address: String, telephone: Int
    ) {
        AsyncTask.execute {
            val customerReference = FirebaseDatabase.getInstance().getReference("Customer")
            val id = customerReference.push().key as String
            val customer = Customer(id, orderId, name, surname, companyName, NIP, email, address, telephone)
            customerReference.child(id).setValue(customer);
        }
    }

    private fun createOrder(
        equipmentsId: ArrayList<String>, customerId: String, name: String, rentalData: String,
        returnData: String, orderPrice: Float, discount: Float
    ) {
        AsyncTask.execute {
            val orderReference = FirebaseDatabase.getInstance().getReference("Order")
            val id = orderReference.push().key as String
            val order = Order(id, equipmentsId, customerId, name, rentalData, returnData, orderPrice, discount)
            orderReference.child(id).setValue(order);
        }

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

    private fun updateEquipment(equipmentID: String, name: String, amount: Int, price: Float) {
        AsyncTask.execute {
            val key = databaseReference.child("Equipment").push().key
            if (key != null) {
                val equipment = Equipment(equipmentID, name, amount, price)
                val postValues = equipment.toMap()
                val childUpdates = HashMap<String, Any>()
                childUpdates["/Equipment/$equipmentID"] = postValues
                databaseReference.updateChildren(childUpdates)
            }
        }
    }


}

