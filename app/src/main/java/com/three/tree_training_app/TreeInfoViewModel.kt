package com.three.tree_training_app

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class TreeInfoViewModel : ViewModel() {
    private var userLocation = MutableLiveData<LatLng>()

    fun setUserLocation(location: LatLng) {
        userLocation.value = location
    }

    fun getUserLocation(): LiveData<LatLng> {
        return userLocation
    }
}
