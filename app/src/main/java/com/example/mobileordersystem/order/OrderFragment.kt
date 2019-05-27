package com.example.mobileordersystem.order

import android.content.Context
import android.content.Intent
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileordersystem.AbstractSwipe
import com.example.mobileordersystem.HomeActivity
import com.example.mobileordersystem.R
import com.example.mobileordersystem.customer.Customer
import com.example.mobileordersystem.equipment.Equipment
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_order.*

class OrderFragment : AbstractSwipe() {

    private val TAG = "OrderFragment"
    lateinit var myAdapter: OrderAdapter
    val orderList: MutableList<Order> = mutableListOf()
    var orderListCopy: MutableList<Order> = mutableListOf()
    val customerList: MutableList<Customer> = mutableListOf()
    private val databaseReference = FirebaseDatabase.getInstance().reference
    var searchPattern: String = ""

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? =
        inflater.inflate(R.layout.fragment_order, container, false)

    companion object {
        fun newInstance(): OrderFragment = OrderFragment()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mContext: Context = context as Context
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
        initSwipe(myAdapter as RecyclerView.Adapter<RecyclerView.ViewHolder>, orderContainer)

        orderContainer.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    addOrder.shrink(true)
                } else {
                    addOrder.extend(true)
                }
            }
        })

        menuTrigger.setOnClickListener {
            (activity as HomeActivity).openDrawer()
        }

        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                search()
            }

        })

    }

    private fun search() {
        if (searchInput != null) {
            searchPattern = searchInput.text.toString()
            if (searchPattern.isBlank()) {
                orderList.clear()
                for (order in orderListCopy) {
                    orderList.add(order)
                }
            } else {
                orderList.clear()
                for (order in orderListCopy) {
                    orderList.add(order)
                    val name = order.name
                    if (!name.contains(searchPattern, true)) {
                        orderList.remove(order)
                    }
                }
            }
            myAdapter.notifyDataSetChanged()
        }
    }

    private fun copyOrders() {
        orderListCopy.clear()
        for (order in orderList) {
            orderListCopy.add(order)
        }
    }


    private fun getOrderList() {
        AsyncTask.execute {
            val orderListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    orderList.clear()
                    dataSnapshot.children.mapNotNullTo(orderList) { it.getValue<Order>(Order::class.java) }
                    Log.i(TAG, orderList.size.toString())
                    copyOrders()
                    search()
                    myAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadPost:onCancelled ${databaseError.toException()}")
                }
            }
            databaseReference.child("Order").addValueEventListener(orderListener)
        }
    }

    override fun delete(holder: RecyclerView.ViewHolder) {
        val ref = FirebaseDatabase.getInstance().getReference("Order")
        val order = myAdapter.items[holder.adapterPosition]
        updateCustomer(order)
        ref.child(order.id).removeValue()
        myAdapter.notifyItemRemoved(holder.adapterPosition)
    }

    override fun edit(holder: RecyclerView.ViewHolder) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun updateEquipment(order: Order) {
        val equipmentList: MutableList<Equipment> = mutableListOf()
        AsyncTask.execute {
            val equipmentListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    equipmentList.clear()
                    dataSnapshot.children.mapNotNullTo(equipmentList) { it.getValue<Equipment>(Equipment::class.java) }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadPost:onCancelled ${databaseError.toException()}")
                }
            }
            databaseReference.child("Equipment").addValueEventListener(equipmentListener)

        }
    }


    private fun updateCustomer(order: Order) {
        AsyncTask.execute {
            val customerListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    customerList.clear()
                    dataSnapshot.children.mapNotNullTo(customerList) { it.getValue<Customer>(Customer::class.java) }
                    for (customer in customerList) {
                        if (customer.customerId == order.customerId) {
                            customer.orderId.remove(order.id)
                            val postValues = customer.toMap()
                            val childUpdates = HashMap<String, Any>()
                            childUpdates["/Customer/${customer.customerId}"] = postValues
                            databaseReference.updateChildren(childUpdates)
                            return
                        }
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadPost:onCancelled ${databaseError.toException()}")
                }
            }
            databaseReference.child("Customer").addListenerForSingleValueEvent(customerListener)
        }
    }

}