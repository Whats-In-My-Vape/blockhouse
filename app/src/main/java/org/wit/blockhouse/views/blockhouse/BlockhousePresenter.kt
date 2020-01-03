package org.wit.blockhouse.views.blockhouse

import android.annotation.SuppressLint
import android.content.Intent
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.blockhouse.helpers.checkLocationPermissions
import org.wit.blockhouse.helpers.createDefaultLocationRequest
import org.wit.blockhouse.helpers.isPermissionGranted

import org.wit.blockhouse.helpers.showImagePicker
import org.wit.blockhouse.models.Location
import org.wit.blockhouse.models.BlockhouseModel
import org.wit.blockhouse.views.*

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class BlockhousePresenter(view: BaseView) : BasePresenter(view) {

    var map: GoogleMap? = null
    var blockhouse = BlockhouseModel()
    var defaultLocation = Location(52.245696, -7.139102, 15f)
    var edit = false;
    var locationService: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(view)
    val locationRequest = createDefaultLocationRequest()

    init {
        if (view.intent.hasExtra("blockhouse_edit")) {
            edit = true
            blockhouse = view.intent.extras?.getParcelable<BlockhouseModel>("blockhouse_edit")!!
            view.showBlockhouse(blockhouse)
        } else {
            if (checkLocationPermissions(view)) {
                doSetCurrentLocation()
            }
        }
    }

    @SuppressLint("MissingPermission")
    fun doSetCurrentLocation() {
        locationService.lastLocation.addOnSuccessListener {
            locationUpdate(Location(it.latitude, it.longitude))
        }
    }


    @SuppressLint("MissingPermission")
    fun doRestartLocationUpdates() {
        var locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult?) {
                if (locationResult != null && locationResult.locations != null) {
                    val l = locationResult.locations.last()
                    locationUpdate(Location(l.latitude, l.longitude))
                }
            }
        }
        if (!edit) {
            locationService.requestLocationUpdates(locationRequest, locationCallback, null)
        }
    }
    fun doAddOrSave(title: String, description: String, favourite: Boolean, rating: Float) {
        blockhouse.title = title
        blockhouse.description = description
        blockhouse.favourite = favourite
        blockhouse.rating = rating
        doAsync {
            if (edit) {
                app.blockhouses.update(blockhouse)
            } else {
                app.blockhouses.create(blockhouse)
            }
            uiThread {
                view?.finish()
            }
        }
    }

    fun doCancel() {
        view?.finish()
    }

    fun doDelete() {
        doAsync {
            app.blockhouses.delete(blockhouse)
            uiThread {
                view?.finish()
            }
        }
    }

    fun doSelectImage() {
        view?.let {
            showImagePicker(view!!, IMAGE_REQUEST)
        }
    }

    fun doSetLocation() {
        view?.navigateTo(VIEW.LOCATION, LOCATION_REQUEST, "location", Location(blockhouse.location.lat, blockhouse.location.lng, blockhouse.location.zoom))
    }

    fun doFavourite() {
       blockhouse.favourite = true
    }

    override fun doRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        if (isPermissionGranted(requestCode, grantResults)) {
            doSetCurrentLocation()
        } else {
            locationUpdate(defaultLocation)
        }
    }

    override fun doActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        when (requestCode) {
            IMAGE_REQUEST -> {
                blockhouse.image = data.data.toString()
                view?.showBlockhouse(blockhouse)
            }
            LOCATION_REQUEST -> {
                val location = data.extras?.getParcelable<Location>("location")!!
                blockhouse.location = location
                locationUpdate(location)
            }
        }
    }

    fun doConfigureMap(m: GoogleMap) {
        map = m
        locationUpdate(blockhouse.location)
    }

    fun locationUpdate(location: Location) {
        blockhouse.location = location
        blockhouse.location.zoom = 15f
        map?.clear()
        map?.uiSettings?.setZoomControlsEnabled(true)
        val options =
            MarkerOptions().title(blockhouse.title).position(LatLng(blockhouse.location.lat, blockhouse.location.lng))
        map?.addMarker(options)
        map?.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                LatLng(blockhouse.location.lat, blockhouse.location.lng),
                blockhouse.location.zoom
            )
        )
        view?.showBlockhouse(blockhouse)
    }
}