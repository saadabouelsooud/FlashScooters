package com.example.flashscooters


import android.arch.core.executor.testing.InstantTaskExecutorRule
import com.example.flashscooters.Model.Local.VehicleDao
import com.example.flashscooters.ViewModel.VehicleViewModel
import com.google.common.truth.Truth
import net.gahfy.mvvmposts.model.database.AppDatabase
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit


class VehicleViewModelTest
{
    @get:Rule
    val mockitoRule = MockitoJUnit.rule()

    @get:Rule
    val taskExecutorRule = InstantTaskExecutorRule()


    @Before
    fun init() {
        MockitoAnnotations.initMocks(this)
    }

    @Mock
    lateinit var appDatabase: AppDatabase
    @Mock
    lateinit var vehicleDao: VehicleDao

    @InjectMocks
    lateinit var classUnderTest: VehicleViewModel
    @Test
    fun `init method sets liveData value to empty list`() {
        val liveDataUnderTest = classUnderTest.vehicleRetrieved.testObserver()

        Truth.assert_()
            .that(liveDataUnderTest.observedValues)
            .isEqualTo(listOf(emptyList<String>()))
    }


}