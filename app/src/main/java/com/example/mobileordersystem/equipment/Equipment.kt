package com.example.mobileordersystem.equipment

import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties

@IgnoreExtraProperties

class Equipment(
    var id: String,
    var name: String,
    var amount: Int,
    var price: Float
) {
    constructor() : this(""," ",0,0f)
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name,
            "amount" to amount,
            "price" to price

        )
    }

}