package com.vroomvroom.android.viewmodel

import android.annotation.SuppressLint
import android.location.Location
import android.os.Looper
import androidx.lifecycle.*
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.vroomvroom.android.domain.db.UserLocationEntity
import com.vroomvroom.android.repository.local.RoomRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import javax.inject.Inject

@ExperimentalCoroutinesApi
@HiltViewModel
class LocationViewModel @Inject constructor(
    private val roomRepository: RoomRepository,
    private val fusedLocationProviderClient: FusedLocationProviderClient,
) : ViewModel() {

    private val _currentLocation by lazy { MutableLiveData<Location>() }
    val currentLocation: MutableLiveData<Location>
        get() = _currentLocation

    val userLocation = roomRepository.getUserLocation()
    val deliveryRange: List<String> = arrayListOf("Bacacay", "Legazpi City",
        "Ligao", "Malilipot", "Malinao", "Santo Domingo", "Tabaco City", "Tiwi")

    private val locationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult) {
            super.onLocationResult(locationResult)
            val location: Location? = locationResult.lastLocation
            location?.let {
                _currentLocation.postValue(it)
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun requestLocationUpdates() {
        val locationRequest = LocationRequest.create().apply {
            priority = LocationRequest.PRIORITY_HIGH_ACCURACY
            interval = 5000
            fastestInterval = 2000
        }
        fusedLocationProviderClient.requestLocationUpdates(
            locationRequest,
            locationCallback,
            Looper.getMainLooper()
        )
    }

    fun removeLocationUpdates() {
        fusedLocationProviderClient.removeLocationUpdates(locationCallback)
    }

    fun insertLocation(userLocationEntity: UserLocationEntity) {
        viewModelScope.launch {
            roomRepository.insertLocation(userLocationEntity)
        }
    }
}