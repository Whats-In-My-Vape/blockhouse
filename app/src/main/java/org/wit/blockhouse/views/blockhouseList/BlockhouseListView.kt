package org.wit.blockhouse.views.blockhouseList


import android.content.Intent
import android.os.Bundle
import android.view.*
import androidx.recyclerview.widget.LinearLayoutManager
import org.wit.blockhouse.R
import org.wit.blockhouse.models.BlockhouseModel
import org.wit.blockhouse.views.BaseView
import kotlinx.android.synthetic.main.activity_blockhouse_list.*

class BlockhouseListView : BaseView(), BlockhouseListener {


    lateinit var presenter: BlockhouseListPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_blockhouse_list)
//        setSupportActionBar(toolbar)
        super.init(toolbar, false)
        presenter = initPresenter(BlockhouseListPresenter(this)) as BlockhouseListPresenter
        val layoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager
        presenter.loadBlockhouses()

    }


    override fun showBlockhouses(blockhouses: List<BlockhouseModel>) {
        recyclerView.adapter = BlockhouseAdapter(blockhouses, this)
        recyclerView.adapter?.notifyDataSetChanged()
    }
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.item_add -> presenter.doAddBlockhouse()
            R.id.item_map -> presenter.doShowBlockhousesMap()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBlockhouseClick(blockhouse: BlockhouseModel) {
        presenter.doEditBlockhouse(blockhouse)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        presenter.loadBlockhouses()
        super.onActivityResult(requestCode, resultCode, data)
    }
}


