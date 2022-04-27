package com.three.tree_training_app

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng

class TreeInfoViewModel : ViewModel() {
    var userLocation = MutableLiveData<LatLng>()

    fun setUserLocation(location: LatLng) {
        userLocation.value = location
    }
}