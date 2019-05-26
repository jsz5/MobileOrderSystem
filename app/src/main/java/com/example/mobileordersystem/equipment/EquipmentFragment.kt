package com.example.mobileordersystem.equipment

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.AsyncTask
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileordersystem.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_equipment.*



class EquipmentFragment : androidx.fragment.app.Fragment() {

    private val TAG = "EquipmentFragment"
    lateinit var myAdapter: EquipmentAdapter
    var item=0
    val equipmentList: MutableList<Equipment> = mutableListOf()

    private val p = Paint()
    private val databaseReference = FirebaseDatabase.getInstance().reference

    companion object {
        fun newInstance(): EquipmentFragment = EquipmentFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        return inflater.inflate(R.layout.fragment_equipment, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val mContext: Context = context as Context
        myAdapter = EquipmentAdapter(equipmentList, mContext)
        getEquipmentList()
        Log.i(TAG, equipmentList.size.toString())
        eqContainer.layoutManager = LinearLayoutManager(context)
        eqContainer.adapter = myAdapter
        eqContainer.itemAnimator = DefaultItemAnimator()
        initSwipe()

        eqContainer.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) {
                    addEquipment.shrink(true)
                } else {
                    addEquipment.extend(true)
                }
            }
        })


    }

    private fun getEquipmentList() {
        AsyncTask.execute {
            val equipmentListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    equipmentList.clear()
                    dataSnapshot.children.mapNotNullTo(equipmentList) { it.getValue<Equipment>(Equipment::class.java) }
                    Log.i(TAG, equipmentList.size.toString())
                    myAdapter.notifyDataSetChanged()
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadPost:onCancelled ${databaseError.toException()}")
                }
            }
            databaseReference.child("Equipment").addValueEventListener(equipmentListener)
        }
    }

    private fun initSwipe() {
        val simpleItemTouchCallback =
            object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {

                override fun onMove(
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    target: RecyclerView.ViewHolder
                ): Boolean {
                    return false
                }

                override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                    if (direction == ItemTouchHelper.LEFT) {
                        item=viewHolder.adapterPosition
                        confirmDialog(viewHolder.adapterPosition)
                    } else {
                        val intent = Intent(activity, CreateEquipment::class.java)
                        startActivity(intent)
                        myAdapter.notifyItemChanged(viewHolder.adapterPosition)
                    }
                }

                override fun onChildDraw(
                    c: Canvas,
                    recyclerView: RecyclerView,
                    viewHolder: RecyclerView.ViewHolder,
                    dX: Float,
                    dY: Float,
                    actionState: Int,
                    isCurrentlyActive: Boolean
                ) {
                    if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                        val itemView = viewHolder.itemView
                        val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                        val width = height / 3

                        if (dX > 0) {
                            val trashBinIcon = resources.getDrawable(
                                R.drawable.edit,
                                null
                            )
                            c.clipRect(
                                itemView.left.toFloat(), itemView.top.toFloat(), dX, itemView.bottom.toFloat()
                            )
                            c.drawColor(Color.parseColor("#388E3C"))
                            trashBinIcon.bounds = Rect(
                                (itemView.left.toFloat() + width).toInt(),
                                (itemView.top.toFloat() + width).toInt(),
                                (itemView.left.toFloat() + 2 * width).toInt(),
                                (itemView.bottom.toFloat() - width).toInt()
                            )

                            trashBinIcon.draw(c)

                        } else {
                            val editIcon = resources.getDrawable(
                                R.drawable.delete,
                                null
                            )
                            c.clipRect(
                                itemView.right.toFloat() + dX,
                                itemView.top.toFloat(),
                                itemView.right.toFloat(),
                                itemView.bottom.toFloat()
                            )

                            c.drawColor(Color.parseColor("#D32F2F"))
                            editIcon.bounds = Rect(
                                (itemView.right.toFloat() - 2 * width).toInt(),
                                (itemView.top.toFloat() + width).toInt(),
                                (itemView.right.toFloat() - width).toInt(),
                                (itemView.bottom.toFloat() - width).toInt()
                            )

                            editIcon.draw(c)

                        }
                    }
                    super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                }
            }
        val itemTouchHelper = ItemTouchHelper(simpleItemTouchCallback)
        itemTouchHelper.attachToRecyclerView(eqContainer)
    }
    protected fun confirmDialog(item: Int){
        val ft = activity!!.supportFragmentManager.beginTransaction()
        val newFragment = SomeDialog(myAdapter, item)
        newFragment.show(ft, "dialog")
    }


}