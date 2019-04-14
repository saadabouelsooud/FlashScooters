package com.example.flashscooters.Model.BasicModels

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class VehicleModel(

    @field:PrimaryKey
    @SerializedName("id")
    val id: String,
    val name: String,
    val description: String,
    val latitude: Double,
    val longitude: Double,
    val batteryLevel: Int,
    val timestamp : String,
    val price : Int,
    val priceTime: Int,
    val currency : String

)