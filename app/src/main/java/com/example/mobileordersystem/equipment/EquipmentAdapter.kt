package com.example.mobileordersystem.equipment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.mobileordersystem.R

class EquipmentAdapter(context: Context, var equipments: MutableList<Equipment>):BaseAdapter(){
    private val inflater: LayoutInflater = LayoutInflater.from(context)
    override fun getItem(position: Int): Any {
        return equipments[position]
    }
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }
    override fun getCount(): Int {
        return equipments.size
    }
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view = convertView
        if (view == null) {
            view = inflater.inflate(R.layout.equipment_item, parent, false)
        }
        view!!.findViewById<TextView>(R.id.name).text = equipments[position].name
        view.findViewById<TextView>(R.id.amount).text=equipments[position].amount.toString()
        view.findViewById<TextView>(R.id.price).text=equipments[position].price.toString()
        return view
    }
}

