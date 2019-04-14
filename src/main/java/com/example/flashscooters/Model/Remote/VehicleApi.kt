package com.example.flashscooters.Model.Remote

import com.example.flashscooters.Model.BasicModels.VehicleModel
import io.reactivex.Observable
import retrofit2.http.GET
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Path


/**
 * The interface which provides methods to get result of webservices
 */
interface VehicleApi {
    /**
     * Get the list of the vehicles from the API
     */
    @GET("/FlashScooters/Challenge/vehicles")
    fun getVehicles(): Observable<List<VehicleModel>>

    @GET("/FlashScooters/Challenge/vehicles/{id}")
    fun getVehicle(@Path("id") id: String): Observable<VehicleModel>

}