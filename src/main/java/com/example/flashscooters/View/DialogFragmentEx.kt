package com.example.flashscooters.View

import android.annotation.SuppressLint
import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.flashscooters.Model.BasicModels.VehicleModel
import com.example.flashscooters.R
import com.example.flashscooters.ViewModel.VehicleViewModel
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.vehicle_card_layout.view.*
import net.gahfy.mvvmposts.injection.ViewModelFactory

@SuppressLint("ValidFragment")
class DialogFragmentEx (var vehicle_id: String, mActivity: AppCompatActivity) : DialogFragment() {

    private lateinit var viewModel: VehicleViewModel
    private val activity = mActivity



    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val vehicleView = inflater.inflate(R.layout.vehicle_card_layout, container, false)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(activity)).get(VehicleViewModel::class.java)
        viewModel.loadVehicle(vehicle_id)

        viewModel.loadingVisibility.observe(this, Observer {
                vehicleView.loading.visibility  = it!!
                vehicleView.container.visibility = View.VISIBLE
        })

        viewModel.vehicleRetrieved.observe(this, Observer { vehicle ->
            vehicleView.vehicle_name.text = vehicle!!.name
            vehicleView.vehicle_name.visibility = View.VISIBLE

            vehicleView.vehicle_desc.text = vehicle!!.description
            vehicleView.vehicle_desc.visibility = View.VISIBLE

            vehicleView.vehicle_price.text = "" + vehicle!!.price
            vehicleView.vehicle_price.visibility = View.VISIBLE


            vehicleView.vehicle_battery_level.text = "" + vehicle!!.batteryLevel
            vehicleView.vehicle_battery_level.visibility = View.VISIBLE

        })

        return vehicleView
    }

}