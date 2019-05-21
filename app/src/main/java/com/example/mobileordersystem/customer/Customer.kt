package com.example.mobileordersystem.customer

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties
class Customer(
    var customerId: String,
    var orderId: String? = null,
    var name: String,
    var surname: String,
    var companyName: String,
    var NIP: Int,
    var email: String,
    var address: String,
    var telephone: Int
) {
    constructor() : this(
        "", " ", "", "", "",
        0, "", "", 0
    )

    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "customerId" to customerId,
            "orderId" to orderId,
            "name" to name,
            "surname" to surname,
            "companyName" to companyName,
            "NIP" to NIP,
            "email" to email,
            "address" to address,
            "telephone" to telephone

        )
    }
}