package com.example.flashscooters.View

import android.arch.lifecycle.Observer
import android.arch.lifecycle.ViewModelProviders
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.flashscooters.Model.BasicModels.VehicleModel
import com.example.flashscooters.R
import com.example.flashscooters.ViewModel.VehicleListViewModel
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import net.gahfy.mvvmposts.injection.ViewModelFactory

class MainActivity : AppCompatActivity() , OnMapReadyCallback, GoogleMap.OnMarkerClickListener {

    private lateinit var viewModel: VehicleListViewModel

    private lateinit var mMap: GoogleMap

    private var vehiclesList: ArrayList<VehicleModel> = ArrayList()

    var fragmentTransaction = supportFragmentManager.beginTransaction()
    var bundle = Bundle()
    var priorInstance = supportFragmentManager.findFragmentByTag("xyz")


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        viewModel = ViewModelProviders.of(this, ViewModelFactory(this)).get(VehicleListViewModel::class.java)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        mMap!!.uiSettings.isZoomControlsEnabled = true
        mMap!!.setMinZoomPreference(14f)


        viewModel.vehicleListRetrieved.observe(this, Observer { vehicleList ->
            // Add a marker in Sydney and move the camera
           vehiclesList = vehicleList!! as ArrayList<VehicleModel>
            for (vehicle in vehiclesList!!)
            {
            val vehicleLocation = LatLng(vehicle.latitude, vehicle.longitude)
            val marker = mMap.addMarker(MarkerOptions().position(vehicleLocation).title(vehicle.name).snippet(""+vehicle.id))
                marker.tag = vehicle
            mMap.moveCamera(CameraUpdateFactory.newLatLng(vehicleLocation))
        }
        })
        mMap.setOnMarkerClickListener(this@MainActivity)
    }

    override fun onMarkerClick(marker: Marker?): Boolean {
        for (vehicle in vehiclesList) {
            if (marker!!.snippet.equals(""+vehicle.id))
            {
                var vehicle: VehicleModel? = marker.tag as VehicleModel?
                showDialogWithValues(vehicle!!.id ,R.id.vehicle_data_card)

            }
        }
        return true
    }

    private fun showDialogWithValues(vehicle_id: String, tag: Int) {
        var dialogFragment = DialogFragmentEx(vehicle_id,this)
        dialogFragment.setArguments(bundle)
        fragmentTransaction = supportFragmentManager.beginTransaction()
        if (priorInstance != null) {
            fragmentTransaction.remove(priorInstance!!)
        }
        fragmentTransaction.addToBackStack(null)
        dialogFragment.show(fragmentTransaction, tag.toString())
    }

}
