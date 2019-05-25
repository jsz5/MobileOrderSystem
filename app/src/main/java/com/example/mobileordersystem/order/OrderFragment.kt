package com.example.mobileordersystem.order

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
import com.example.mobileordersystem.R
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import kotlinx.android.synthetic.main.fragment_order.*

class OrderFragment : androidx.fragment.app.Fragment() {

    private val TAG = "OrderFragment"
    lateinit var myAdapter: OrderAdapter
    val orderList: MutableList<Order> = mutableListOf()
    private val databaseReference = FirebaseDatabase.getInstance().reference

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_order, container, false)

    companion object {
        fun newInstance(): OrderFragment = OrderFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mContext : Context = context as Context
        myAdapter = OrderAdapter(orderList, mContext)
        getOrderList()
        Log.i(TAG, orderList.size.toString())
        orderContainer.layoutManager = LinearLayoutManager(context)
        orderContainer.adapter = myAdapter
        orderContainer.itemAnimator = DefaultItemAnimator()

        addOrder.setOnClickListener {
            val intent = Intent(activity, CreateOrder::class.java)
            startActivity(intent)
        }


        orderContainer.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if(dy > 0) {
                    addOrder.shrink(true)
                } else {
                    addOrder.extend(true)
                }
            }
        })

    }




    private fun getOrderList() {
        AsyncTask.execute {
            val orderListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    orderList.clear()
                    dataSnapshot.children.mapNotNullTo(orderList) { it.getValue<Order>(Order::class.java) }
                    Log.i(TAG, orderList.size.toString())
                    myAdapter.notifyDataSetChanged()
                }
                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadPost:onCancelled ${databaseError.toException()}")
                }
            }
            databaseReference.child("Order").addValueEventListener(orderListener)
        }
    }

}