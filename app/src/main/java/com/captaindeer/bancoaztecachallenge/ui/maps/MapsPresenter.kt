package com.captaindeer.bancoaztecachallenge.ui.maps

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.location.Location
import android.util.Log
import androidx.core.app.ActivityCompat
import com.captaindeer.bancoaztecachallenge.data.remote.responses.models.LocationsModel
import com.captaindeer.bancoaztecachallenge.data.remote.responses.models.PostModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.firebase.firestore.FirebaseFirestore
import java.util.*

class MapsPresenter(val context: Context, val view: MapsInterface.View) : MapsInterface.Presenter {

    private var firestore: FirebaseFirestore? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient


    override fun latLngFirestore() {
        if (ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                context,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            return
        }
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)
        fusedLocationClient.lastLocation
            .addOnSuccessListener { location: Location? ->
                if (location != null) {
                    firestore = FirebaseFirestore.getInstance()
                    firestore!!.collection("locations/").document(UUID.randomUUID().toString())
                        .set(LocationsModel(location.latitude, location.longitude))
                        .addOnCompleteListener {
//View on Success
                        }
                        .addOnFailureListener {
                            //View onError
                        }
                }
            }
    }

    override fun getLatLng() {
        firestore = FirebaseFirestore.getInstance()
        firestore!!.collection("locations").get().addOnSuccessListener {
            if (!it.isEmpty) {
                val locationList = it.toObjects(LocationsModel::class.java)
                Log.e("TAG","este es el tamaño de lo que se trajo de urls "+locationList.size.toString())
                locationList. forEach {
                    view.setLatLng(it.latitude,it.longitude)
                }
            } else {
                return@addOnSuccessListener
            }
        }

    }


}