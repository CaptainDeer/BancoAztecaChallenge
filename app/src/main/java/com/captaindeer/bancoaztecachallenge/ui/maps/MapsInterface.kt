package com.captaindeer.bancoaztecachallenge.ui.maps

interface MapsInterface {

    interface Presenter {
        fun latLngFirestore()
        fun getLatLng()
    }

    interface View {
        fun setLatLng(lat: Double, lng: Double)
    }
}