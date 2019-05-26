package com.example.mobileordersystem.customer

import android.content.Context
import android.content.Intent
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

        val item = items[position]

        val name = "${item.name} ${item.surname}"
        holder.name.text = name
        holder.email.text = item.email

        holder.itemView.setOnClickListener {
            val intent = Intent(context, ShowCustomerActivity::class.java)
            intent.putExtra("customerId", item.customerId)
            intent.putExtra("name", item.name)
            intent.putExtra("surname", item.surname)
            intent.putExtra("companyName", item.companyName)
            intent.putExtra("nip", item.nip)
            intent.putExtra("email", item.email)
            intent.putExtra("address", item.address)
            intent.putExtra("telephone", item.telephone)
            context.startActivity(intent)
            Log.i(TAG, "clicked $position")
        }

    }



}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val name: TextView = view.name
    val email: TextView = view.email
}

