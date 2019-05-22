package com.example.mobileordersystem.customer

import com.google.firebase.database.Exclude
import android.R.attr.name



class Customer(
    var customerId: String,
    var orderId: ArrayList<String>?=null,
    var name: String,
    var surname: String,
    var companyName: String,
    var nip: Int,
    var email: String,
    var address: String,
    var telephone: Int
) {
    constructor() : this("", arrayListOf(),"", "","",0,"","",0)
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "addres" to address,
            "companyName" to companyName,
            "customerId" to customerId,
            "email" to email,
            "name" to name,
            "nip" to nip,
            "surname" to surname,
            "telephone" to telephone
        )
    }
    override fun toString(): String {
        return name+" "+surname
    }

}