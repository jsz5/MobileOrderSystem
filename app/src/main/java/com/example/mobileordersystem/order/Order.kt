package com.example.mobileordersystem.order

import com.google.firebase.database.Exclude
import kotlin.collections.ArrayList


class Order(
    var id: String,
    var equipmentId: ArrayList<String>,
    var amountUsed: ArrayList<Int>,
    var customerId: String,
    var name: String,
    var rentalData: String,
    var returnData: String,
    var orderPrice: Float,
    var discount: Float
) {
    constructor() : this("", arrayListOf(), arrayListOf(),"", "","","", 0f, 0f)
    @Exclude
    fun toMap() : Map<String, Any?> {
        return mapOf(
            "id" to id,
            "equipmentId" to equipmentId,
            "amountUsed" to amountUsed,
            "customerId" to customerId,
            "name" to name,
            "rentalData" to rentalData,
            "returnData" to returnData,
            "orderPrice" to orderPrice,
            "discount" to discount
        )
    }

    override fun toString(): String {
        return name
    }
}
