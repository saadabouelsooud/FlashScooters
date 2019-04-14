package net.gahfy.mvvmposts.injection

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import android.arch.persistence.room.Room
import android.support.v7.app.AppCompatActivity
import com.example.flashscooters.ViewModel.VehicleListViewModel
import com.example.flashscooters.ViewModel.VehicleViewModel
import net.gahfy.mvvmposts.model.database.AppDatabase


class ViewModelFactory(private val activity: AppCompatActivity): ViewModelProvider.Factory{
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(VehicleListViewModel::class.java)) {
            val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "vehicles").build()
            @Suppress("UNCHECKED_CAST")
            return VehicleListViewModel(db.vehicleDao()) as T
        } else  if (modelClass.isAssignableFrom(VehicleViewModel::class.java)) {
            val db = Room.databaseBuilder(activity.applicationContext, AppDatabase::class.java, "vehicles").build()
            @Suppress("UNCHECKED_CAST")
            return VehicleViewModel(db.vehicleDao()) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")

    }
}