package org.wit.blockhouse.views.map

import android.os.Bundle
import org.wit.blockhouse.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import org.wit.blockhouse.helpers.readImageFromPath
import org.wit.blockhouse.views.BaseView
import kotlinx.android.synthetic.main.activity_blockhouse_map.*
import kotlinx.android.synthetic.main.content_blockhouse_maps.*
import org.wit.blockhouse.models.BlockhouseModel

class BlockhouseMapView : BaseView(), GoogleMap.OnMarkerClickListener {

    lateinit var presenter: BlockhouseMapPresenter
    lateinit var map : GoogleMap


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blockhouse_map)
        super.init(toolbar, true)
        presenter = initPresenter (BlockhouseMapPresenter(this)) as BlockhouseMapPresenter

        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync {
            map = it
            map.setOnMarkerClickListener(this)
            presenter.loadBlockhouses()
        }
    }

    override fun showBlockhouse(blockhouse: BlockhouseModel) {
        currentTitle.text = blockhouse.title
        currentDescription.text = blockhouse.description
        currentImage.setImageBitmap(readImageFromPath(this, blockhouse.image))
    }


    override fun showBlockhouses(blockhouses: List<BlockhouseModel>) {
        presenter.doPopulateMap(map, blockhouses)
    }

    override fun onMarkerClick(marker: Marker): Boolean {
        presenter.doMarkerSelected(marker)
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mapView.onLowMemory()
    }

    override fun onPause() {
        super.onPause()
        mapView.onPause()
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}