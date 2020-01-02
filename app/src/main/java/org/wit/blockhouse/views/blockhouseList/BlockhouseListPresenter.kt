package org.wit.blockhouse.views.blockhouseList

import org.jetbrains.anko.doAsync
import org.jetbrains.anko.uiThread
import org.wit.blockhouse.views.BaseView
import org.wit.blockhouse.views.BasePresenter
import org.wit.blockhouse.views.VIEW
import org.wit.blockhouse.models.BlockhouseModel

class BlockhouseListPresenter (view: BaseView) : BasePresenter(view){


    fun doAddBlockhouse() {
        view?.navigateTo(VIEW.BLOCKHOUSE)
    }

    fun doEditBlockhouse(blockhouse: BlockhouseModel) {
        view?.navigateTo(VIEW.BLOCKHOUSE, 0, "blockhouse_edit", blockhouse)
    }

    fun doShowBlockhousesMap() {
        view?.navigateTo(VIEW.MAPS)
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