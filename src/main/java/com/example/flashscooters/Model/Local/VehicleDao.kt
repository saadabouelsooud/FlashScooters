package com.example.flashscooters.Model.Local

import android.arch.lifecycle.ViewModel
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.example.flashscooters.Model.BasicModels.VehicleModel

@Dao
interface VehicleDao {
    @get:Query("SELECT * FROM VehicleModel")
    val all: List<VehicleModel>

    @Insert
    fun insertAll(vararg users: VehicleModel)

    @Query("SELECT * FROM VehicleModel WHERE id == :id ")
    fun getVehicleById(id: String) : VehicleModel

}