package com.example.mobileordersystem.customer

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mobileordersystem.R
import kotlinx.android.synthetic.main.customer_item.view.*

class CustomerAdapter(val items : MutableList<Customer>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    private val TAG = "CustomerAdapter"


    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.customer_item, parent, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val name = "${items[position].name} ${items[position].surname}"
        holder.name.text = name
        holder.email.text = items[position].email
    }



}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val name: TextView = view.name
    val email: TextView = view.email
}

