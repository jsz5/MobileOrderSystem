package com.example.mobileordersystem.equipment

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileordersystem.AbstractSwipe
import com.example.mobileordersystem.R
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_equipment.*



class EquipmentFragment : AbstractSwipe() {

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

        val mContext: Context = context as Context
        myAdapter = EquipmentAdapter(equipmentList, mContext)
        getEquipmentList()
        Log.i(TAG, equipmentList.size.toString())
        eqContainer.layoutManager = LinearLayoutManager(context)
        eqContainer.adapter = myAdapter
        eqContainer.itemAnimator = DefaultItemAnimator()
        addEquipment.setOnClickListener{
            val intent = Intent(context, CreateEquipment::class.java)
            startActivity(intent)
        }
        initSwipe(myAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>, eqContainer)

        eqContainer.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    addEquipment.shrink(true)
                } else {
                    addEquipment.extend(true)
                }
            }
        })
    }

    private fun getEquipmentList() {
        AsyncTask.execute {
            val equipmentListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    equipmentList.clear()
                    dataSnapshot.children.mapNotNullTo(equipmentList) { it.getValue<Equipment>(Equipment::class.java) }
                    Log.i(TAG, equipmentList.size.toString())
                    myAdapter.notifyDataSetChanged()
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadPost:onCancelled ${databaseError.toException()}")
                }
            }
            databaseReference.child("Equipment").addValueEventListener(equipmentListener)
        }
    }


    override fun delete(holder: RecyclerView.ViewHolder){
        val ref = FirebaseDatabase.getInstance().getReference("Equipment")
        val equipment=myAdapter.items[holder.adapterPosition]
        if(equipment.orders.isEmpty()) {
            ref.child(equipment.id).removeValue()
            myAdapter.notifyItemRemoved(holder.adapterPosition)
        }else
        {
            myAdapter.notifyItemChanged(holder.adapterPosition)
            Snackbar.make(view as View, R.string.eq_with_order, Snackbar.LENGTH_LONG).show()
        }
    }

    override fun edit(holder: RecyclerView.ViewHolder) {
        val item=myAdapter.items[holder.adapterPosition]
        val intent = Intent(activity, ShowEquipmentActivity::class.java)
        intent.putExtra("id", item.id)
        intent.putExtra("name", item.name)
        intent.putExtra("amount", item.amount)
        intent.putExtra("amountLeft", item.amountLeft)
        intent.putExtra("price", item.price)
        startActivity(intent)
    }


}