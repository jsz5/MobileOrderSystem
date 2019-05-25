package com.example.mobileordersystem.order

import kotlin.collections.ArrayList


data class Order(
    var id: String,
    var equipmentId: ArrayList<String>,
    var customerId: String,
    var name: String,
    var rentalDate: String,
    var returnDate: String,
    var orderPrice: Float,
    var discount: Float
) {
}