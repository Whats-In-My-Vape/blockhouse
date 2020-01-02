package org.wit.blockhouse.views.map

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import org.wit.blockhouse.models.BlockhouseModel
import org.wit.blockhouse.views.BasePresenter
import org.wit.blockhouse.views.BaseView
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread

class BlockhouseMapPresenter(view: BaseView) : BasePresenter(view) {


    fun doPopulateMap(map: GoogleMap, blockhouses: List<BlockhouseModel>) {
        map.uiSettings.setZoomControlsEnabled(true)
        blockhouses.forEach {
            val loc = LatLng(it.location.lat, it.location.lng)
            val options = MarkerOptions().title(it.title).position(loc)
            map.addMarker(options).tag = it.id
            map.moveCamera(CameraUpdateFactory.newLatLngZoom(loc, it.location.zoom))
        }
    }

    fun doMarkerSelected(marker: Marker) {
        val tag = marker.tag as Long
        doAsync {
            val blockhouse = app.blockhouses.findById(tag)
            uiThread {
                if (blockhouse != null) view?.showBlockhouse(blockhouse)
            }
        }
    }

    fun loadBlockhouses() {
        doAsync {
            val blockhouses = app.blockhouses.findAll()
            uiThread {
                view?.showBlockhouses(blockhouses)
            }
        }
    }
}