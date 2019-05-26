package com.example.mobileordersystem.equipment

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
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

        val item = items[position]
        val price = "${item.price}zł"
        val amount = "${item.amountLeft}/${item.amount}"

        holder.name.text = item.name
        holder.amount.text = amount
        holder.price.text = price
        holder.itemView.setOnClickListener {
            val intent = Intent(context, ShowEquipmentActivity::class.java)
            intent.putExtra("id", item.id)
            intent.putExtra("name", item.name)
            intent.putExtra("amount", item.amount)
            intent.putExtra("amountLeft", item.amountLeft)
            intent.putExtra("price", item.price)
            context.startActivity(intent)
            Log.i(TAG, "clicked $position")
        }
    }



}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {

    val name: TextView = view.name
    val amount: TextView = view.amount
    val price: TextView = view.price
}

