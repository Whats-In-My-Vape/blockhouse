package org.wit.blockhouse.views

import android.content.Intent

import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import org.jetbrains.anko.AnkoLogger


import org.wit.blockhouse.models.BlockhouseModel
import org.wit.blockhouse.models.Location

import org.wit.blockhouse.views.editLocation.EditLocationView
import org.wit.blockhouse.views.blockhouse.BlockhouseView
import org.wit.blockhouse.views.blockhouseList.BlockhouseListView
import org.wit.blockhouse.views.map.BlockhouseMapView

val IMAGE_REQUEST = 1
val LOCATION_REQUEST = 2

enum class VIEW {
    LOCATION, BLOCKHOUSE, MAPS, LIST
}

open abstract class BaseView() : AppCompatActivity(), AnkoLogger {

    var basePresenter: BasePresenter? = null

    fun navigateTo(view: VIEW, code: Int = 0, key: String = "", value: Parcelable? = null) {
        var intent = Intent(this, BlockhouseListView::class.java)
        when (view) {
            VIEW.LOCATION -> intent = Intent(this, EditLocationView::class.java)
            VIEW.BLOCKHOUSE -> intent = Intent(this, BlockhouseView::class.java)
            VIEW.MAPS -> intent = Intent(this, BlockhouseMapView::class.java)
            VIEW.LIST -> intent = Intent(this, BlockhouseListView::class.java)
        }
        if (key != "") {
            intent.putExtra(key, value)
        }
        startActivityForResult(intent, code)
    }

    fun initPresenter(presenter: BasePresenter): BasePresenter {
        basePresenter = presenter
        return presenter
    }

    fun init(toolbar: Toolbar, upEnabled: Boolean) {
        toolbar.title = title
        setSupportActionBar(toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(upEnabled)
    }

    override fun onDestroy() {
        basePresenter?.onDestroy()
        super.onDestroy()
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            basePresenter?.doActivityResult(requestCode, resultCode, data)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        basePresenter?.doRequestPermissionsResult(requestCode, permissions, grantResults)
    }

    open fun showBlockhouse(blockhouse: BlockhouseModel) {}
    open fun showBlockhouses(blockhouses: List<BlockhouseModel>) {}
    open fun showProgress() {}
    open fun hideProgress() {}
    open fun showLocation(location : Location) {}
}