package net.gahfy.mvvmposts.model.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.RoomDatabase
import com.example.flashscooters.Model.BasicModels.VehicleModel
import com.example.flashscooters.Model.Local.VehicleDao


@Database(entities = [VehicleModel::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun vehicleDao(): VehicleDao
}