package com.example.mobileordersystem.customer

class Customer(
    var customerId: String,
    var orderId: String?=null,
    var name: String,
    var surname: String,
    var companyName: String,
    var NIP: Int,
    var email: String,
    var address: String,
    var telephone: Int
) {
}