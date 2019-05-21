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
    private val TAG = "EquipmentAdapter"

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.equipment_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val price = "${items[position].price}zł"
        var amount = "${items[position].amount}"
        amount = "$amount/$amount"

        holder.name.text = items[position].name
        holder.amount.text = amount
        holder.price.text = price
    }



}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    val name: TextView = view.name
    val amount: TextView = view.amount
    val price: TextView = view.price
}

