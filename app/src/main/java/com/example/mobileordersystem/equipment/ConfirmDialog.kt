package com.example.mobileordersystem.equipment

import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment

class SomeDialog(
   var  myAdapter: EquipmentAdapter,
    var item: Int
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return AlertDialog.Builder(activity)
            .setTitle("Usuwanie")
            .setMessage("Czy jesteś pewien, że chcesz kontynuuować?")
            .setNegativeButton(android.R.string.no) { dialog, _ ->
                dialog.dismiss()
                myAdapter.notifyItemChanged(item)
            }
            .setPositiveButton(android.R.string.yes
            ) { _, _ ->
                myAdapter.notifyItemRemoved(item)
                }
            .create()
    }
}