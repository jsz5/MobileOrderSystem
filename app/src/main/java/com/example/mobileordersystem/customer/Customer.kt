package com.example.mobileordersystem.customer

import com.google.firebase.database.Exclude

class Customer(
    var customerId: String,
    var orderId: String?=null,
    var name: String,
    var surname: String,
    var companyName: String,
    var nip: Int,
    var email: String,
    var address: String,
    var telephone: Int
) {
    constructor() : this(""," ","", "","",0,"","",0)
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

}