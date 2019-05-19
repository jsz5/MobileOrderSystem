package com.example.mobileordersystem.equipment

import android.content.Context
import android.util.Log
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.mobileordersystem.R
import kotlinx.android.synthetic.main.equipment_item.view.*

class EquipmentAdapter(val items : MutableList<Equipment>, val context: Context) : androidx.recyclerview.widget.RecyclerView.Adapter<ViewHolder>() {


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

class ViewHolder (view: View) : androidx.recyclerview.widget.RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val name = view.name
    val amount = view.amount
    val price = view.price
}





//class EquipmentAdapter(context: Context, var equipments: MutableList<Equipment>):BaseAdapter(){
//    private val inflater: LayoutInflater = LayoutInflater.from(context)
//    override fun getItem(position: Int): Any {
//        return equipments[position]
//    }
//    override fun getItemId(position: Int): Long {
//        return position.toLong()
//    }
//    override fun getCount(): Int {
//        return equipments.size
//    }
//    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
//        var view = convertView
//        if (view == null) {
//            view = inflater.inflate(R.layout.equipment_item, parent, false)
//        }
//        view!!.findViewById<TextView>(R.id.name).text = equipments[position].name
//        view.findViewById<TextView>(R.id.amount).text=equipments[position].amount.toString()
//        view.findViewById<TextView>(R.id.price).text=equipments[position].price.toString()
//        return view
//    }
//}

