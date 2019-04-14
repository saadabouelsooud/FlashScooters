package com.example.flashscooters.ViewModel

import android.arch.lifecycle.MutableLiveData
import android.util.Log
import android.view.View
import com.example.flashscooters.Model.BasicModels.VehicleModel
import com.example.flashscooters.Model.Local.VehicleDao
import com.example.flashscooters.Model.Remote.VehicleApi
import com.example.flashscooters.R
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import net.gahfy.mvvmposts.base.BaseViewModel
import javax.inject.Inject


class VehicleViewModel(private val vehicleDao: VehicleDao):BaseViewModel() {

    @Inject
    lateinit var vehicleApi: VehicleApi
    val vehicleRetrieved: MutableLiveData<VehicleModel> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    private lateinit var subscription: Disposable


    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    public fun loadVehicle(id: String) {
        subscription = Observable.fromCallable { vehicleDao.getVehicleById(id) }
            .concatMap {
                    dbVehicle ->
                if(true) // to request remote every time
                    vehicleApi.getVehicle(id).concatMap {
                            apiVehicle-> vehicleDao.getVehicleById(id)
                        Observable.just(apiVehicle)
                    }
                else
                    Observable.just(dbVehicle)
            }
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { onRetrieveVehicleListStart() }
            .doOnTerminate { onRetrieveVehicleFinish() }
            .subscribe(
                { result ->
                    onRetrieveVehicleSuccess(result)
                    Log.d("vehicle desc",result.description)
                },
                { onRetrieveVehicleError() }
            )    }

    private fun onRetrieveVehicleListStart(){
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveVehicleFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveVehicleSuccess(vehicle: VehicleModel){
        vehicleRetrieved.value = vehicle
    }

    private fun onRetrieveVehicleError(){
        errorMessage.value = R.string.post_error
    }

}