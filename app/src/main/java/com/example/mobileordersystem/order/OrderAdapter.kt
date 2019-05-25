package com.example.mobileordersystem.order

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileordersystem.R
import kotlinx.android.synthetic.main.order_item.view.*

class OrderAdapter(val items : MutableList<Order>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {
    private val TAG = "OrderAdapter"

    override fun getItemCount(): Int {
        return items.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.order_item, parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        val rentalDate = items[position].rentalDate
        val returnDate = items[position].returnDate

        holder.name.text = items[position].name
        holder.rentalDate.text = rentalDate
        holder.returnDate.text = returnDate
    }

}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    val name: TextView = view.name
    val rentalDate: TextView = view.rentalDate
    val returnDate: TextView = view.returnDate
}

