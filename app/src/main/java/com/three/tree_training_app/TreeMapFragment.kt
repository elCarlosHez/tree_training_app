package com.three.tree_training_app

import android.Manifest
import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class TreeMapFragment : Fragment() {

    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var googleMap: GoogleMap
    private val model: TreeInfoViewModel by activityViewModels()

    private val callback = OnMapReadyCallback { googleMap ->
        this.googleMap = googleMap
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_tree_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        // Request user location to configure our map fragment
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    markUserLocation()
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    markUserLocation()
                } else -> {
                // No location access granted.
                }
            }
        }

        // Request user location permission
        locationPermissionRequest.launch(arrayOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION))
    }

    @SuppressLint("MissingPermission")
    private fun markUserLocation() {
        activity?.let {
            fusedLocationClient = LocationServices.getFusedLocationProviderClient(it)
            fusedLocationClient.lastLocation
                .addOnSuccessListener { location->
                    if (location != null) {
                        // Add a mark and move the map to the user location
                        val userLocation = LatLng(location.latitude, location.longitude)
                        model.setUserLocation(userLocation)
                        this.googleMap.addMarker(MarkerOptions().position(userLocation))
                        this.googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLocation, 10f))
                    }
                }
        }
    }
}