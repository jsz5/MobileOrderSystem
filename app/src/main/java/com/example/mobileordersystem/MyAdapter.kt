package com.example.mobileordersystem

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.cell_layout.view.*
import java.io.File

class MyAdapter(val items : ArrayList<String>, val context: Context) : RecyclerView.Adapter<ViewHolder>() {


    // Gets the number of animals in the list
    override fun getItemCount(): Int {
        return items.size
    }

    // Inflates the item views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(LayoutInflater.from(context).inflate(R.layout.cell_layout, parent, false))
    }

    // Binds each animal in the ArrayList to a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val data = items[position].split(";")

        if(data[1] == "filePath") {
            Glide
                .with(context)
                .load(File(data[3]))
                .thumbnail(Glide.with(context).load(R.drawable.placeholder))
                .override(270, 700)
                .centerCrop()
                .into(holder.image)
        } else {
            Glide
                .with(context)
                .load(data[1])
                .thumbnail(Glide.with(context).load(R.drawable.placeholder))
                .override(270, 700)
                .centerCrop()
                .into(holder.image)
        }
        holder.rating.rating = data[0].toFloat()
        holder.image.isClickable = true

    }
}

class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
    val image = view.imageView
    val rating = view.ratingBar
}