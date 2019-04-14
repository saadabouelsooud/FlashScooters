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

class VehicleListViewModel(private val vehicleDao: VehicleDao): BaseViewModel(){
    @Inject
    lateinit var vehicleApi: VehicleApi

    val vehicleListRetrieved: MutableLiveData<List<VehicleModel>> = MutableLiveData()
    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()
    val errorMessage:MutableLiveData<Int> = MutableLiveData()
    val errorClickListener = View.OnClickListener { loadVehicles() }

    private lateinit var subscription: Disposable

    init{
        loadVehicles()
    }

    override fun onCleared() {
        super.onCleared()
        subscription.dispose()
    }

    private fun loadVehicles(){
         subscription = Observable.fromCallable { vehicleDao.all }
                        .concatMap {
                                dbVehiclesList ->
                            if(dbVehiclesList.isEmpty())
                                vehicleApi.getVehicles().concatMap {
                                        apiVehiclesList -> vehicleDao.insertAll(*apiVehiclesList.toTypedArray())
                                    Observable.just(apiVehiclesList)
                                }
                            else
                                Observable.just(dbVehiclesList)
                        }
                        .subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread())
                        .doOnSubscribe { onRetrievePostListStart() }
                        .doOnTerminate { onRetrieveVehicleListFinish() }
                        .subscribe(
                            { result ->
                                onRetrieveVehicleListSuccess(result)
                                Log.d("first vehicle name",result[1].description)
                            },
                            { onRetrievePostListError() }
                )
    }

    private fun onRetrievePostListStart(){
        loadingVisibility.value = View.VISIBLE
        errorMessage.value = null
    }

    private fun onRetrieveVehicleListFinish(){
        loadingVisibility.value = View.GONE
    }

    private fun onRetrieveVehicleListSuccess(postList:List<VehicleModel>){
        vehicleListRetrieved.value = postList
    }

    private fun onRetrievePostListError(){
        errorMessage.value = R.string.post_error
    }

}