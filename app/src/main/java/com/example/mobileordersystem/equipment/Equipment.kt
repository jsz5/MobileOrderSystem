package com.example.mobileordersystem.equipment

import android.content.res.Resources
import androidx.core.content.res.TypedArrayUtils.getString
import com.example.mobileordersystem.R
import com.google.firebase.database.Exclude
import com.google.firebase.database.IgnoreExtraProperties
import java.io.Serializable

@IgnoreExtraProperties

class Equipment(
    var id: String,
    var name: String,
    var amount: Int,
    var amountLeft: Int,
    var price: Float
) : Serializable {
    constructor() : this(""," ",0,0,0f)
    @Exclude
    fun toMap(): Map<String, Any?> {
        return mapOf(
            "id" to id,
            "name" to name,
            "amount" to amount,
            "amountLeft" to amountLeft,
            "price" to price

        )
    }
    override fun toString(): String {
        return name+" "+amountLeft+"/"+amount +" "+price+" zł"
    }


}