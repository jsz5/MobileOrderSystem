package com.example.mobileordersystem.order

import android.app.DatePickerDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import androidx.fragment.app.DialogFragment
import com.example.mobileordersystem.R
import java.text.DateFormat
import java.util.*



class DatePicker : DialogFragment(), DatePickerDialog.OnDateSetListener {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {

        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        return DatePickerDialog(activity, this, year, month, day) //today is the default value
    }

    override fun onDateSet(view: DatePicker, year: Int, month: Int, day: Int) {
        var calendar:Calendar=Calendar.getInstance()
        calendar.set(year,month,day)
        val formatter=DateFormat.getDateInstance(DateFormat.MEDIUM)
        val dateButton = arguments!!.getString("dateButton")
        if(dateButton=="rental") {
            activity?.findViewById<Button>(R.id.rentalDateInput)?.text = formatter.format(calendar.time)
        }else if(dateButton=="return"){
            activity?.findViewById<Button>(R.id.returnDateInput)?.text = formatter.format(calendar.time)
        }
    }
}
