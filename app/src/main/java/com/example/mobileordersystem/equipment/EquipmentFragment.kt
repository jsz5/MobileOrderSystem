package com.example.mobileordersystem.equipment

import android.content.Context
import android.content.Intent
import android.graphics.*
import android.os.AsyncTask
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileordersystem.HomeActivity
import com.example.mobileordersystem.R
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_equipment.*


class EquipmentFragment : androidx.fragment.app.Fragment() {

    private val TAG = "EquipmentFragment"
    lateinit var myAdapter: EquipmentAdapter
    var item = 0
    val equipmentList: MutableList<Equipment> = mutableListOf()
    var equipmentListCopy: MutableList<Equipment> = mutableListOf()
    private val p = Paint()
    private val databaseReference = FirebaseDatabase.getInstance().reference
    var searchPattern: String = ""
    lateinit var sortType : String

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

        sortType = resources.getString(R.string.sort_by_name)

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

        addEquipment.setOnClickListener {
            val intent = Intent(context, CreateEquipment::class.java)
            startActivity(intent)
        }

        menuTrigger.setOnClickListener {
            (activity as HomeActivity).openDrawer()
        }


        searchInput.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {

            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                search()
            }

        })


        sortrigger.setOnClickListener {
            val popup = PopupMenu(context,it)
            popup.menuInflater.inflate(R.menu.equipment_sort_menu, popup.menu)

            popup.setOnMenuItemClickListener { menuItem ->
                sortType = menuItem.title.toString()
                sort()
                Log.i(TAG, menuItem.title.toString())
                true
            }
            popup.show()
        }

    }

    private fun sort() {
        when(sortType) {
            resources.getString(R.string.sort_by_name) -> {
                equipmentList.sortBy { it.name.toLowerCase() }
                copyEquipment()
                myAdapter.notifyDataSetChanged()
            }
            resources.getString(R.string.sort_by_price) -> {
                equipmentList.sortBy { it.name.toLowerCase() }
                equipmentList.sortBy { it.price }
                copyEquipment()
                myAdapter.notifyDataSetChanged()
            }
            resources.getString(R.string.sort_by_amount) -> {
                equipmentList.sortBy { it.name.toLowerCase() }
                equipmentList.sortBy { it.amount }
                copyEquipment()
                myAdapter.notifyDataSetChanged()
            }
            resources.getString(R.string.sort_by_amount_left) -> {
                equipmentList.sortBy { it.name.toLowerCase() }
                equipmentList.sortBy { it.amountLeft }
                copyEquipment()
                myAdapter.notifyDataSetChanged()
            }
        }

    }

    private fun search() {
        if(searchInput != null) {
            searchPattern = searchInput.text.toString()
            if (searchPattern.isBlank()) {
                equipmentList.clear()
                for (order in equipmentListCopy) {
                    equipmentList.add(order)
                }
            } else {
                equipmentList.clear()
                for (order in equipmentListCopy) {
                    equipmentList.add(order)
                    val name = order.name
                    if (!name.contains(searchPattern, true)) {
                        equipmentList.remove(order)
                    }
                }
            }
            myAdapter.notifyDataSetChanged()
        }
    }

    private fun copyEquipment() {
        equipmentListCopy.clear()
        for(order in  equipmentList) {
            equipmentListCopy.add(order)
        }
    }

    private fun getEquipmentList() {
        AsyncTask.execute {
            val equipmentListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    equipmentList.clear()
                    dataSnapshot.children.mapNotNullTo(equipmentList) { it.getValue<Equipment>(Equipment::class.java) }
                    Log.i(TAG, equipmentList.size.toString())
                    if(context != null) {
                        sort()
                    }
                    copyEquipment()
                    search()
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
                        item = viewHolder.adapterPosition
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

    protected fun confirmDialog(item: Int) {
        val ft = activity!!.supportFragmentManager.beginTransaction()
        val newFragment = SomeDialog(myAdapter, item)
        newFragment.show(ft, "dialog")
    }


}