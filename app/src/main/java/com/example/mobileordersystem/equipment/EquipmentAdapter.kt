package com.example.mobileordersystem.equipment

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.mobileordersystem.R
import kotlinx.android.synthetic.main.equipment_item.view.*

class EquipmentAdapter(val items : MutableList<Equipment>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {


    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.equipment_item, parent, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Log.i("eq", items[position].name)
        holder.name.text = items[position].name
        holder.amount.text = items[position].amount.toString()
        holder.price.text = items[position].price.toString()
    }



}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val name: TextView = view.name
    val amount: TextView = view.amount
    val price: TextView = view.price
}

