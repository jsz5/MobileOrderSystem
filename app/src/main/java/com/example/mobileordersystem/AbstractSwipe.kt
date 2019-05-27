package com.example.mobileordersystem

import android.app.AlertDialog
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Rect
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

abstract class AbstractSwipe : androidx.fragment.app.Fragment() {
    var item = 0
    protected fun initSwipe(myAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>, container: RecyclerView) {
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
                        confirmDialog(viewHolder, myAdapter)
                    } else {
                        edit(viewHolder)
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
        itemTouchHelper.attachToRecyclerView(container)
    }

    private fun confirmDialog(holder: RecyclerView.ViewHolder, myAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>) {
        val newFragment = AlertDialog.Builder(activity)
            .setTitle(R.string.delete)
            .setMessage(R.string.confirm)
            .setNegativeButton(android.R.string.no) { dialog, _ ->
                dialog.dismiss()
                myAdapter.notifyItemChanged(holder.adapterPosition)
            }
            .setPositiveButton(
                android.R.string.yes
            ) { _, _ ->
                delete(holder)
            }
            .create()
        newFragment.show()
    }

    abstract fun delete(holder: RecyclerView.ViewHolder)
    abstract fun edit(holder: RecyclerView.ViewHolder)
}