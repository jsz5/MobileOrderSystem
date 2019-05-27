package com.example.mobileordersystem.order

import android.app.Activity
import android.content.Intent
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.mobileordersystem.R
import com.example.mobileordersystem.customer.CreateCustomer
import com.example.mobileordersystem.customer.Customer
import com.example.mobileordersystem.equipment.Equipment
import com.google.firebase.database.*
import kotlinx.android.synthetic.main.fragment_create_order.*

import android.view.View
import android.widget.*
import android.widget.TextView
import android.widget.LinearLayout

import java.lang.Exception
import android.view.ViewGroup
import android.widget.EditText
import android.text.Editable
import android.text.TextWatcher
import com.example.mobileordersystem.AbstractDataUpdate
import java.text.DateFormat
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap
import android.widget.ArrayAdapter
import android.widget.Toast

@Suppress("DEPRECATION")
class CreateOrder : AbstractDataUpdate() {
    private val customerList: MutableList<Customer> = mutableListOf()
    private val equipmentList: MutableList<Equipment> = mutableListOf()

    private lateinit var databaseReference: DatabaseReference
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.fragment_create_order)
        addCustomer.setOnClickListener {
            val intent = Intent(this, CreateCustomer::class.java)
            startActivity(intent);
        }
        databaseReference = FirebaseDatabase.getInstance().reference
        setDatePickers()
        setCustomerSpinner()
        setEquipmentSpinner(equipmentInput)
        setAddEquipment()
        saveOrder()
        findViewById<TextView>(R.id.sumPriceInput).text = 0.toString()
        addPriceListener(amount, equipmentInput)
        addDiscountListener()

    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)

        for (i in 0..linearLayout.childCount) {
            val view = linearLayout.getChildAt(i)
            if (view is EditText) {
                outState.putString(view.id.toString(), view.text.toString())
            }
        }
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {
        super.onRestoreInstanceState(savedInstanceState)
        for (i in 0..linearLayout.childCount) {
            val view = linearLayout.getChildAt(i)
            if (view is EditText) {
                val text = savedInstanceState?.getString(view.id.toString())
                view.setText(text)
            }
        }
    }

    private fun setDatePickers() {
        var calendar: Calendar = Calendar.getInstance()
        calendar.set(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.MONTH))
        val formatter = DateFormat.getDateInstance(DateFormat.MEDIUM)
        returnDateInput.text = formatter.format(calendar.time)
        rentalDateInput.text = formatter.format(calendar.time)
        rentalDateInput.setOnClickListener {
            val newFragment = DatePicker()
            val bundle = Bundle()
            bundle.putString("dateButton", "rental")
            newFragment.arguments = bundle
            newFragment.show(supportFragmentManager, "datePicker")
        }
        returnDateInput.setOnClickListener {
            val newFragment = DatePicker()
            val bundle = Bundle()
            bundle.putString("dateButton", "return")
            newFragment.arguments = bundle
            newFragment.show(supportFragmentManager, "datePicker")
        }
    }

    private fun setCustomerSpinner() {
        AsyncTask.execute {
            val customerListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    customerList.clear()
                    dataSnapshot.children.mapNotNullTo(customerList) { it.getValue<Customer>(Customer::class.java) }
                    val spinner: Spinner = customerInput
                    val spinnerArrayAdapter =
                        ArrayAdapter<Customer>(this@CreateOrder, android.R.layout.simple_spinner_item, customerList)

                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = spinnerArrayAdapter
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadPost:onCancelled ${databaseError.toException()}")
                }
            }
            databaseReference.child("Customer").addListenerForSingleValueEvent(customerListener)
        }
    }

    private fun setAddEquipment() {
        addEquipment.setOnClickListener {
            val layout = newSpinner as LinearLayout
            LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
            )
            layout.orientation = LinearLayout.VERTICAL
            val newSpinner = Spinner(this)
            val numberInput = EditText(this)

            layout.addView(newSpinner)
            layout.addView(numberInput)
            numberInput.setTextAppearance(this, R.style.Nasz)
            numberInput.apply {
                hint ="Ilość"

            }
            setEquipmentSpinner(newSpinner)
            addPriceListener(numberInput, newSpinner)
        }
    }

    private fun setEquipmentSpinner(spinner: Spinner) {
        AsyncTask.execute {
            val equipmentListener = object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    equipmentList.clear()
                    dataSnapshot.children.mapNotNullTo(equipmentList) { it.getValue<Equipment>(Equipment::class.java) }
                    val spinnerArrayAdapter =
                        ArrayAdapter<Equipment>(
                            this@CreateOrder,
                            android.R.layout.simple_spinner_item,
                            equipmentList
                        )

                    spinnerArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
                    spinner.adapter = spinnerArrayAdapter
                    spinnerArrayAdapter.notifyDataSetChanged()

                }

                override fun onCancelled(databaseError: DatabaseError) {
                    println("loadPost:onCancelled ${databaseError.toException()}")
                }
            }
            databaseReference.child("Equipment").addListenerForSingleValueEvent(equipmentListener)

        }
    }

    private fun saveOrder() {
        save.setOnClickListener {
            var notAvailable = false;
            val layout = newSpinner as LinearLayout
            var i = 0
            val equipments = arrayListOf<String>()
            val amountUsed= arrayListOf<Int>()
            val orderId = FirebaseDatabase.getInstance().getReference("Order").push().key as String
            while (i + 1 < layout.childCount) {
                val spinner = layout.getChildAt(i) as Spinner
                val amount = layout.getChildAt(i + 1) as EditText
                val equipment = spinner.selectedItem as Equipment
                val amountValue: Int
                if (amount.text.toString() == "") {
                    amountValue = 0;
                } else {
                    amountValue = amount.text.toString().toInt()
                }
                if (equipment.amountLeft - amountValue >= 0) {
                    updateEquipment(equipment, equipment.amountLeft - amountValue)
                    equipments.add(equipment.id)
                    amountUsed.add(equipment.amountLeft - amountValue)
                    addOrderToEquipment(orderId, equipment)
                } else {
                    notAvailable = true
                    Toast.makeText(
                        this, resources.getString(R.string.not_available),
                        Toast.LENGTH_LONG
                    ).show()
                }
                i += 2
            }
            val customer = customerInput.selectedItem as Customer
            val discount: Float = if (discountInput.text.toString() != "") {
                discountInput.text.toString().toFloat()
            } else {
                0f
            }
            if (!notAvailable && rentalDateInput.text.toString() <= returnDateInput.text.toString() &&
                sumPriceInput.text.toString() != "" && sumPriceInput.text.toString().toFloat() != 0f
            ) {
                createOrder(
                    orderId,
                    equipments,
                    amountUsed,
                    customer.customerId,
                    nameInput.text.toString(),
                    rentalDateInput.text.toString(),
                    returnDateInput.text.toString(),
                    sumPriceInput.text.toString().toFloat(),
                    discount,
                    customer
                )
                finish()
            } else {
                Toast.makeText(
                    this, resources.getString(R.string.failure),
                    Toast.LENGTH_LONG
                ).show()
                //ToDo FIX R.id.content=null
//                fail(findViewById(R.id.content), R.string.failure)
            }
        }
    }

    private fun createOrder(id: String,
        equipmentsId: ArrayList<String>, amountUsed: ArrayList<Int>, customerId: String, name: String, rentalData: String,
        returnData: String, orderPrice: Float, discount: Float, customer: Customer
    ) {
        try {
            AsyncTask.execute {
                val orderReference=FirebaseDatabase.getInstance().getReference("Order")
                val order = Order(id, equipmentsId,amountUsed, customerId, name, rentalData, returnData, orderPrice, discount)
                orderReference.child(id).setValue(order);
                updateCustomer(id, customer)

            }
        } catch (e: Exception) {
            fail(findViewById(R.id.content), R.string.failure)
        }

    }

    private fun updateCustomer(orderId: String, customer: Customer) {
        try {
            AsyncTask.execute {
                val key = databaseReference.child("Customer").push().key
                if (key != null) {
                    customer.orderId.add(orderId)
                    val postValues = customer.toMap()
                    val childUpdates = HashMap<String, Any>()
                    childUpdates["/Customer/${customer.customerId}"] = postValues
                    databaseReference.updateChildren(childUpdates)
                }
            }
        } catch (e: Exception) {
            fail(findViewById(R.id.content), R.string.failure)
        }
    }

    private fun addOrderToEquipment(orderId: String, equipment: Equipment) {
        try {
            AsyncTask.execute {
                val key = databaseReference.child("Equipment").push().key
                if (key != null) {
                    equipment.orders.add(orderId)
                    val postValues = equipment.toMap()
                    val childUpdates = HashMap<String, Any>()
                    childUpdates["/Equipment/${equipment.id}"] = postValues
                    databaseReference.updateChildren(childUpdates)
                }
            }
        } catch (e: Exception) {
            fail(findViewById(R.id.content), R.string.failure)
        }
    }

    private fun updateEquipment(equipment: Equipment, amountLeft: Int) {
        try {
            AsyncTask.execute {
                val key = databaseReference.child("Equipment").push().key
                if (key != null) {
                    equipment.amountLeft = amountLeft
                    val postValues = equipment.toMap()
                    val childUpdates = HashMap<String, Any>()
                    childUpdates["/Equipment/${equipment.id}"] = postValues
                    databaseReference.updateChildren(childUpdates)
                }
            }
        } catch (e: Exception) {
            fail(findViewById(R.id.content), R.string.failure)
        }
    }

    private fun addPriceListener(editText: EditText, spinner: Spinner) {
        editText.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                try {
                    val selectedItem = spinner.selectedItem as Equipment
                    val old = sumPriceInput.text.toString().toFloat()
                    val new = old + selectedItem.price * editText.text.toString().toInt()
                    sumPriceInput.text = new.toString()
                } catch (e: Exception) {
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                try {
                    val selectedItem = spinner.selectedItem as Equipment
                    val old = sumPriceInput.text.toString().toFloat()
                    val new = old - selectedItem.price * editText.text.toString().toInt()
                    sumPriceInput.text = new.toString()
                } catch (e: Exception) {
                }
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })
    }

    private fun addDiscountListener() {
        discountInput.addTextChangedListener(object : TextWatcher {

            override fun afterTextChanged(s: Editable) {
                try {
                    val discountValue =
                        sumPriceInput.text.toString().toFloat() * discountInput.text.toString().toInt() / 100f
                    sumPriceInput.text = (sumPriceInput.text.toString().toFloat() - discountValue).toString()

                } catch (e: Exception) {
                }
            }

            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
                try {
                    val withoutDiscount =
                        sumPriceInput.text.toString().toFloat() / (1 - discountInput.text.toString().toFloat() / 100f)
                    sumPriceInput.text = withoutDiscount.toString()
                } catch (e: Exception) {
                }
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
            }
        })
    }


}


