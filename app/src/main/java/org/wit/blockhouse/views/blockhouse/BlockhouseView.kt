package org.wit.blockhouse.views.blockhouse

import android.content.Intent
import android.os.Bundle
import android.os.PersistableBundle
import android.view.Menu
import android.view.MenuItem
import org.wit.blockhouse.R
import org.wit.blockhouse.helpers.readImageFromPath
import org.wit.blockhouse.models.BlockhouseModel
import org.wit.blockhouse.models.Location
import org.wit.blockhouse.views.BaseView
import kotlinx.android.synthetic.main.activity_blockhouse.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.info
import org.jetbrains.anko.toast



class BlockhouseView : BaseView(), AnkoLogger {

    lateinit var presenter: BlockhousePresenter
    var blockhouse = BlockhouseModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blockhouse)
        super.init(toolbarAdd, true)
        info("Blockhouse Activity initialized")
        presenter = initPresenter (BlockhousePresenter(this)) as BlockhousePresenter
        mapView.onCreate(savedInstanceState)
        mapView.getMapAsync {
            presenter.doConfigureMap(it)
            it.setOnMapClickListener { presenter.doSetLocation() }
        }
        chooseImage.setOnClickListener { presenter.doSelectImage() }
    }

    override fun showBlockhouse(blockhouse: BlockhouseModel) {
        blockhouseName.setText(blockhouse.title)
        description.setText(blockhouse.description)
        blockhouseImage.setImageBitmap(readImageFromPath(this, blockhouse.image))
        chooseImage.setText(R.string.change_image)
        this.showLocation(blockhouse.location)
    }

    override fun showLocation(location: Location) {
        lat.setText("%.6f".format(location.lat))
        lng.setText("%.6f".format(location.lng))
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_blockhouse, menu)
        if (presenter.edit && menu != null) menu.getItem(1).setVisible(true)
        return super.onCreateOptionsMenu(menu)
    }

    // activity that checks that a button is pressed and reports back boolean
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_delete -> {
                presenter.doDelete()
                finish()
            }
            R.id.item_save -> {
                if (blockhouseName.text.toString().isEmpty()) {
                    toast(R.string.hint_blockhouseName)
                } else {
                    presenter.doAddOrSave(blockhouseName.text.toString(), description.text.toString())
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            presenter.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onBackPressed() {
        presenter.doCancel()
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
        presenter.doRestartLocationUpdates()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState)
    }
}