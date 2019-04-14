package net.gahfy.mvvmposts.injection.component

import com.example.flashscooters.ViewModel.VehicleListViewModel
import com.example.flashscooters.ViewModel.VehicleViewModel
import dagger.Component
import net.gahfy.mvvmposts.injection.module.NetworkModule
import javax.inject.Singleton

/**
 * Component providing inject() methods for presenters.
 */
@Singleton
@Component(modules = [(NetworkModule::class)])
interface ViewModelInjector {
    /**
     * Injects required dependencies into the specified PostListViewModel.
     * @param vehicleListViewModel VehicleListViewModel in which to inject the dependencies
     */
    fun inject(vehicleListViewModel: VehicleListViewModel)
    /**
     * Injects required dependencies into the specified PostViewModel.
     * @param vehicleViewModel VehicleViewModel in which to inject the dependencies
     */
    fun inject(vehicleViewModel: VehicleViewModel)

    @Component.Builder
    interface Builder {
        fun build(): ViewModelInjector

        fun networkModule(networkModule: NetworkModule): Builder
    }
}